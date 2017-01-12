import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  //semantive controller

  "TaskController" should {

    "return list of current tasks" in {

      contentAsString(route(app, FakeRequest(GET, "/report")).get) mustBe "raport: time to do every task: 0"
      contentAsString(route(app, FakeRequest(GET, "/add?taskType=N&name=wy_task&content=content&time=1234")).get) mustBe "type: N name: wy_task content: content time: 1234 status: IN_PROGRESS"


    }

  }

}

