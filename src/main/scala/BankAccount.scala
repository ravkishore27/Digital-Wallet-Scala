import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
//import javax.persistence.GeneratedValue
//import java.lang.Long

//import org.springframework.boot._
//import org.springframework.boot.autoconfigure._
//import org.springframework.stereotype._

//import org.springframework.web.bind.annotation._
//import java.util._
//import scala.collection.JavaConversions._
//import java.sql.Timestamp
//import java.util.Date
//import java.text.DateFormat
//import java.text.SimpleDateFormat
//import java.util.Calendar

@Entity
class BankAccount {

@BeanProperty
var bid: String = _

@BeanProperty
@NotEmpty
var account_name: String = _
  
@BeanProperty
@NotEmpty
var routing_number: String = _

@BeanProperty
@NotEmpty
var account_number: String = _
}