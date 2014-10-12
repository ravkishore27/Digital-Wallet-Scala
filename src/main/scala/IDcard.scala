import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import java.util.Date

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
import java.util.Calendar
import com.fasterxml.jackson.annotation.JsonInclude._
import com.fasterxml.jackson.databind.ObjectMapper */

@Entity
class IDcard {

@BeanProperty
var cid: String = _

@BeanProperty
@NotEmpty
var cardName: String = _
  
@BeanProperty
@NotEmpty
var cardNumber: String = _

@BeanProperty
var expDate: Date = _
}