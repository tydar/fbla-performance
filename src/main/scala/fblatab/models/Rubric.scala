package fblatab.models

import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.record.field.StringField
import net.liftweb.mongodb.record.field.MongoListField
import net.liftweb.mongodb.record.MongoId
import net.liftweb.record.field.IntField
import net.liftweb.util._
import Helpers._
import S._

class Rubric(rec: OwnerType) extends MongoRecord[Rubric] with MongoId[Rubric] {
  def meta = Rubric
  
  def owner = rec
  
  object event extends StringField(this, 30)
  object given extends IntField(this, 0)
  object doesNotMeet extends SlidingIntField(this)
  object meets extends SlidingIntField(this)
  object exceeds extends SlidingIntField(this)
  
  def render = {
  }
}

class SlidingIntField(rec: OwnerType) extends MongoRecord[SlidingIntField] with MongoId[SlidingIntField] {
  
  def meta = SlidingIntField
  def owner = rec
  
  object value extends IntField(this)
  object min extends IntField(this)
  object max extends IntField(this)
  
  def setMin(n: Int) = {
    this.min.set(n)
  }
  
  def setMax(n: Int) = {
    this.max.set(n)
  }
  
  def setValue(n: Int) = {
    if(this.min.get <= n && n <= this.max.get) {
      value.set(n)
    }
  }
}