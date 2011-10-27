package fblatab.models
import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.record.field.StringField
import net.liftweb.record.field.BooleanField
import net.liftweb.util._
import Helpers._
import S._

class User(rec: OwnerType) extends MongoRecord[User] with MongoId[User]{
  def meta = User
  def owner = rec
  
  object uname extends StringField(this, 15)
  object first extends StringField(this, 30)
  object middle extends StringField(this, 1)
  object last extends StringField(this, 30)
  object password extends StringField(this, 30)
  object judge extends BooleanField(this)
  object event extends StringField(this, 65)
  
  def this(rec: OwnerType, uname: String, pass: String) = {
    this(rec)
    this.uname.set(uname)
    this.password.set(pass)
  }
}