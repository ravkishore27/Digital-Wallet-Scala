import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.sql.Timestamp
/*
import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot._
import org.springframework.boot.autoconfigure._
import org.springframework.stereotype._
import org.springframework.web.bind.annotation._
import java.util._
import scala.collection.JavaConversions._
import java.sql.Timestamp
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar */

@Entity
class User {
  
@BeanProperty
var uid: String = _

@BeanProperty
@NotEmpty
var email: String = _
  
@BeanProperty
@NotEmpty
var password: String = _

@BeanProperty
var name: String = _

val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
val date = new Date()

@BeanProperty
var created_at: String = dateFormat.format(date)
@BeanProperty
var updated_at: String = dateFormat.format(date)

  
}