package fblatab.users.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._

import com.foursquare.rogue.Rogue._
import net.liftweb.common.{Box, Full, Empty}
import net.liftweb.http.{S, RequestVar}

class Judge extends MongoRecord[Judge] with ObjectIdPk[Judge] {
  def meta = Judge

  object username extends StringField(this, 15)
  object first extends StringField(this, 30)
  object middle extends OptionalStringField(this, 30)
  object last extends StringField(this, 30)
  object preferred extends OptionalStringField(this, 30)
  object email extends EmailField(this, 100)
  object password extends MongoPasswordField(this, 5)

  def displayName = {
    preferred.get.getOrElse(first.get) + " " + last.get
  }

  def formalName = {
    last.get + ", " + first.get
  }

  def login(): Unit = {
    Judge.current.set(Full(this.id.get))
  }

  def logout(): Unit = {
    Judge.current.set(Empty)
  }
}

object Judge extends Judge with MongoMetaRecord[Judge] {
  ensureIndex("username" -> 1, "unique" -> true)
  ensureIndex("email" -> 1, ("sparse" -> true) ~ ("unique" -> true))
  ensureIndex(("last" -> 1) ~ ("first" -> 1))

  override def collectionName = "judges"

  private object current extends SessionVar[Box[ObjectId]](Empty)
  private object currentUser extends RequestVar[Box[Judge]](current.get.flatMap(Judge.find(_)))

  def getByUsername(username: String): Box[Judge] = {
    Judge where (_.username eqs username) get()
  }

  def authenticate(username: String, password: String): Box[Judge] = {
    val usersByName = Judge.findAll("username" -> username)
    usersByName match {
      case user :: Nil => authenticate(user, password)
      case _ => Empty //more than one user, WHAT
    }
  }

  def authenticate(user: Judge, password: String): Box[Judge] = {
    if(user.password.isMatch(password)){
      Full(user)
    } else {
      Empty
    }
  }

  def loggedIn_? = current.isDefined

  def getCurrent: Box[Judge] = current.get.flatMap(Judge.find(_))

  def getCurrentOrRedirect(): Judge = getCurrent openOr {
    S.notice("You must log in to access that page.")
    S.redirectTo("/users/login")
  }
}

