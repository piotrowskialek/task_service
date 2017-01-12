package controllers

import play.api.mvc._
import javax.inject._
import models._
import Array._

/**
  * Created by apiotro on 11.01.17.
  * Simple task logic handler.
  */

@Singleton
class TaskController @Inject() extends Controller {

  var toDoTasks = new Array[Task](0)
  var doneTasks = new Array[Task](0)

  def addNewTask(taskType: Char, name: String, content: String, time: Int, deadlineOrCycle: Int = -1) = Action {

    try {

      if (time > 0 && List('N', 'C', 'D').contains(taskType) && toDoTasks.filter( (task: Task) => task.name.equals(name)).length == 0 ) {

        val newTask: Task = taskType match {

          case 'N' => new NeverendingTask(name, content, time, models.Status.apply(0))
          case 'C' => new CyclicTask(name, content, time, deadlineOrCycle, models.Status.apply(0))
          case 'D' => new DeadlineTask(name, content, time, deadlineOrCycle, models.Status.apply(0))
        }

        toDoTasks = toDoTasks :+ newTask

        Ok("type: " + taskType + " name: " + name + " content: " + content + " time: " + time + " status: " + newTask.taskStatus)

      } else
          throw new Exception("wrong time or type")

    } catch {
        case e: Exception => BadRequest("Request rejected.")
    }
  }

  def changeStatus(name: String, newStatus: Int) = Action {

    try {
      if(newStatus!=1 && newStatus!=2)
        throw new Exception("wrong status; try 1 or 2")

      var index = toDoTasks.indexOf(toDoTasks.find(task => task.name.equals(name)).get)
      val tmp = toDoTasks.apply(index)
      tmp.taskStatus = models.Status.apply(newStatus)
      toDoTasks = toDoTasks.take(index)
      doneTasks = doneTasks :+ tmp

      Ok("Status updated. " + tmp)

      //move to done tasks

    } catch {
        case e: Exception => BadRequest("Request rejected.")
   }
  }

  def changeTimeEstimation(name: String, newTime: Int) = Action {

    try {
      if(newTime<=0)
        throw new Exception("time must be greater than 0")

      var index = toDoTasks.indexOf(toDoTasks.find(task => task.name.equals(name)).get)
      val tmp = toDoTasks.apply(index)
      tmp.timeEstimation = newTime
      toDoTasks.update(index, tmp)
      Ok("Time updated. " + tmp)

    } catch {
      case e: Exception => BadRequest("Request rejected.")
    }
  }

  def view = Action {
    //harmonogram

    var todoInfo: String = "TODO Tasks: \n"
    toDoTasks.foreach((task) => todoInfo += " Name: " + task.name + " Content: " + task.content +
      " time estimation: " + task.timeEstimation + " status: " + task.taskStatus + '\n')

    var doneInfo: String = "Done Tasks: \n"
    doneTasks.foreach((task) => doneInfo += " Name: " + task.name + " Content: " + task.content +
      " time estimation: " + task.timeEstimation + " status: " + task.taskStatus + '\n')

    var noTimeTasksInfo: String = "Non deadline Tasks: \n"
    toDoTasks.filter((task: Task) => task.isInstanceOf[NeverendingTask]).foreach((task) =>
      noTimeTasksInfo += " Name: " + task.name + " Content: " + task.content +
        " time estimation: " + task.timeEstimation + " status: " + task.taskStatus + '\n')

    Ok(todoInfo + doneInfo + noTimeTasksInfo)

  }

  def createReport = Action {      //raport

    var timeReport: Int = 0
    toDoTasks.foreach( (task: Task) =>  timeReport += task.timeEstimation )

    Ok("raport: time to do every task: " + timeReport)

  }

}
