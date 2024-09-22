package u07.simulation.facade

import java.awt.*
import java.util.concurrent.LinkedBlockingQueue
import javax.swing.*
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import scala.util.Try

object SwingFunctionalFacade:

  def createFrame(): Frame = new FrameImpl();

  trait Frame:
    def setSize(width: Int, height: Int): Frame
    def createChart(title: String, xLabel: String, yLabel: String, rowLabels: Iterable[String]): Frame
    def addChartValue(x: Double, y: Double, rowKey: String): Frame
    def addChartValues(values: Map[String, Double], x: Double): Frame
    def schedule(millis: Int, eventName: String): Frame
    def show(): Frame
    def events(): () => String

  class FrameImpl extends Frame:
    private val jFrame: JFrame = new JFrame()
    private var jChart: LineChart2D = LineChart2D.create()
    private var chartRowLabels: Iterable[String] = Seq()
    private val eventQueue: LinkedBlockingQueue[String] = new LinkedBlockingQueue()
    override def events(): () => String = () =>
      Try(eventQueue.take()).getOrElse("")

    this.jFrame.setLayout(new GridLayout(1, 1))
    this.jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE)

    override def setSize(width: Int, height: Int): Frame =
      this.jFrame.setSize(width, height)
      this

    override def createChart(title: String, xLabel: String, yLabel: String, rowLabels: Iterable[String]): Frame =
      jChart = LineChart2D.create(title, xLabel, yLabel, rowLabels)
      jFrame.setContentPane(jChart.asJPanel)
      jFrame.pack()
      chartRowLabels = rowLabels
      this

    override def addChartValue(x: Double, y: Double, rowKey: String): Frame =
      jChart.addValue(x, y, rowKey)
      this

    override def addChartValues(values: Map[String, Double], x: Double): Frame =
      values.foreach: (rowId, y) =>
        jChart.addValue(x, y, rowId)
      chartRowLabels.filter(!values.contains(_)).foreach:
        jChart.addValue(x, 0, _)
      this

    override def schedule(millis: Int, eventName: String): Frame =
      val timer = new Timer(millis, _ => Try(eventQueue.put(eventName)))
      timer.setRepeats(false)
      timer.start()
      this

    override def show(): Frame =
      jFrame.setVisible(true)
      this
