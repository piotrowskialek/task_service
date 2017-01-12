import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends PlaySpec with OneServerPerTest with OneBrowserPerTest with HtmlUnitFactory {

  "Application" should {

    "work from within a browser" in {

      /**
        * checking /semantive view for a key word "TaskController"
        */

      go to ("http://localhost:" + port + "/semantive")
      pageSource must include ("TODO Tasks:")

    }
  }
}

