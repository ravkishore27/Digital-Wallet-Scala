import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty

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