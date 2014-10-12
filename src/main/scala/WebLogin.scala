import javax.persistence.Id
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty

@Entity
class WebLogin {

@BeanProperty
var lid: String = _

@BeanProperty
@NotEmpty
var url: String = _
  
@BeanProperty
@NotEmpty
var login: String = _

@BeanProperty
@NotEmpty
var password: String = _
}