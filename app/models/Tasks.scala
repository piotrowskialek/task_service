package models

/**
  * Created by apiotro on 11.01.17.
  */

object Status extends Enumeration {

  type Status = Value
  val IN_PROGRESS,DONE,CANCELED = Value
}

abstract class Task (var name: String, var content: String, var timeEstimation: Int, var taskStatus: Status.Value)

class NeverendingTask(name: String, content: String, timeEstimation: Int, taskStatus: Status.Value)
  extends Task(name, content, timeEstimation, taskStatus)

class CyclicTask(name: String, content: String, timeEstimation: Int, var cycle: Int, taskStatus: Status.Value)
  extends Task(name, content, timeEstimation, taskStatus)

class DeadlineTask(name: String, content: String, timeEstimation: Int, var deadline: Int, taskStatus: Status.Value)
  extends Task(name, content, timeEstimation, taskStatus)

