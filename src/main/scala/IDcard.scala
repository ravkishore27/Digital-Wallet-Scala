import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import java.util.Date

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