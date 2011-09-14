/*
 * This class is the model for a scoresheet in a particular event
 *
 *
 */

package fblatab.event.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._

import com.foursquare.rogue.Rogue._
import net.liftweb.common.{Box, Full, Empty}

class Event extends MongoRecord[Event] with ObjectPkId[Event]  {
  def meta = Event


}