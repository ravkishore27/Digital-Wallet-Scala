
import java.lang.Long
import scala.beans.BeanProperty
import org.springframework.boot._
import org.springframework.boot.autoconfigure._
import org.springframework.stereotype._
import org.springframework.web.bind.annotation._
import java.util._
import scala.collection.JavaConversions._
import java.sql.Timestamp
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import java.text.SimpleDateFormat
import org.springframework.http._
import javax.validation.Valid


object UserController {
	def main(args:Array[String]) {
		SpringApplication.run(classOf[UserController])
	}
}
@Controller
@EnableAutoConfiguration
@RequestMapping(Array("/api/v1/users"))
	class UserController {
	
	  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
	  val randomGenerator = new Random()
	  var userList = new Array[Map[String, String]](100)
	  var idCardList = new ArrayList[Map[String, String]]()
	  var webLoginList = new ArrayList[Map[String, String]]()
	  var bankAccountList = new ArrayList[Map[String, String]]()
	  var z = new Array[Map[String, String]](1)
	  var userid : Int = 1
	    
	  
	  @RequestMapping(method =Array(RequestMethod.POST))
	  @ResponseStatus( HttpStatus.CREATED )
	  @ResponseBody
	  private def addUser(@Valid @RequestBody user :User) : Map[String, String] = {
	    
	    user.uid = userid.toString
	    var userDetails = scala.collection.mutable.Map(
	    	"user_id" -> userid.toString,
	    	"email" -> user.getEmail,
	    	"password" -> user.getPassword,
	    	"created_at" -> user.getCreated_at,
	    	"updated_at" -> user.getUpdated_at
	    	)	
	    if(user.getName != null) userDetails.put("name", user.getName)
	    										
	    userList(userid-1) = userDetails
	    userid = userid + 1
	    userList(userid - 2)
	  }
	    
	@RequestMapping(value=Array("{uid}"),method =Array(RequestMethod.GET))
	@ResponseBody
	def viewUser(@PathVariable uid: Int): ResponseEntity[String] = {
			
		if(userList(uid-1) == null){
			    return new ResponseEntity[String]("No user found for the mentioned User ID", HttpStatus.NOT_FOUND);
			}else{			  
				return new ResponseEntity[String]((userList(uid-1)).toString, HttpStatus.OK);
			}
		}

	@RequestMapping(value=Array("{uid}"),method =Array(RequestMethod.PUT))
	@ResponseBody
	def updateUser(@Valid @RequestBody user :User, @PathVariable uid: String): ResponseEntity[String] = {
	  
	  if(userList(uid.toInt -1) == null){
			    return new ResponseEntity[String]("No user found for the mentioned User ID", HttpStatus.NOT_FOUND);
			}else{
				  var userString = userList(uid.toInt - 1)
				  userString.keys.foreach{ i =>  
				    						if(i.equalsIgnoreCase("name")) user.setName(userString(i))
				    						if(i.equalsIgnoreCase("created_at")) user.setCreated_at(userString(i))
				    						if(i.equalsIgnoreCase("user_id")) user.setUid(userString(i))
				    						var updatedDate = new Date()
				    						var ud = dateFormat.format(updatedDate)
				    						user.setUpdated_at(ud)	}	
				  user.setEmail(user.getEmail)
				  user.setPassword(user.getPassword)
				  
				  var userDetails = scala.collection.mutable.Map(
				  	"user_id" -> user.getUid,
				    "email" -> user.getEmail,
				    "password" -> user.getPassword,
				    "created_at" -> user.getCreated_at,
				    "updated_at" -> user.getUpdated_at
				    )	
				  if(user.getName != null) userDetails.put("name", user.getName)
				  userList(uid.toInt - 1) = userDetails
				  return new ResponseEntity[String]((userList(uid.toInt-1)).toString, HttpStatus.OK);
			}
	}
	  
	@RequestMapping(value=Array("{uid}/idcards"),method =Array(RequestMethod.POST))
	@ResponseBody
	def addIdCard(@Valid @RequestBody idcard :IDcard, @PathVariable uid:String) : ResponseEntity[String] = {
	   
	   var userString = userList(uid.toInt - 1)
	   var randomId = randomGenerator.nextInt(1000000)
	   var card_id : String =  randomId.toString()
	   if( userString == null){
			return new ResponseEntity[String]("User ID does not exist", HttpStatus.NOT_FOUND);
		}else{
			idcard.cid = card_id
			
			var cardDetails = scala.collection.mutable.Map(
				"user_id" -> uid,
				"card_id" -> card_id,
			    "cardName" -> idcard.cardName,
			    "cardNumber" -> idcard.cardNumber
			    )
			    if(idcard.expDate  != null) cardDetails.put("expDate", idcard.expDate.toString() )
			    idCardList.add(cardDetails)
			    z(0) = cardDetails
			    return new ResponseEntity[String]((z(0)).toString(), HttpStatus.CREATED);
		}
	  }
	
	@RequestMapping(value=Array("{uid}/idcards"),method =Array(RequestMethod.GET))
	@ResponseBody
		def viewIDcards(@PathVariable uid: String) : ResponseEntity[String] = {
		  var list : String = ""
			 for(x <- idCardList){
				if(x != null) 
				  x.keys.foreach{i => if(i.equalsIgnoreCase("user_id") && x(i).equals(uid))
				    list += x
				}
			 }
		  if(!(list.contains(uid))){
			    return new ResponseEntity[String]("No cards found for the specified user", HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity[String](list, HttpStatus.OK);
			}
		}
	
	@RequestMapping(value=Array("{uid}/idcards/{cid}"),method =Array(RequestMethod.DELETE))
	@ResponseBody
		def deleteIDcard(@PathVariable uid : String, @PathVariable cid : String) : ResponseEntity[String] = {
			var status = false;
			var itr = idCardList.iterator
			while(itr.hasNext()){
			  	var x = itr.next()
			  	x.keys.foreach{
				  	i => if(i.equalsIgnoreCase("card_id") && x(i).equals(cid)){
				  		itr.remove()
				  		status = true 
				  		} 
			  	}    
		}	
			if(status){
		  return new ResponseEntity[String]("Deleted", HttpStatus.NO_CONTENT);
		}else{
		  return new ResponseEntity[String]("Card ID does not exist", HttpStatus.NOT_FOUND);
		}
	}
	  
	@RequestMapping(value=Array("{uid}/weblogins"),method =Array(RequestMethod.POST))
	@ResponseBody
	def addWebLogins(@Valid @RequestBody weblogin :WebLogin, @PathVariable uid:String) : ResponseEntity[String] = {
	   var userString = userList(uid.toInt - 1)
	   var randomId = randomGenerator.nextInt(1000000)
	   var login_id : String = randomId.toString()
		if(userString == null){
			return new ResponseEntity[String]("User ID does not exist", HttpStatus.NOT_FOUND);
		}else{
			weblogin.lid = login_id
			var loginDetails = scala.collection.mutable.Map(
				"user_id" -> uid,
				"login_id" -> login_id,
			    "url" -> weblogin.url,
			    "login" -> weblogin.login ,	
			    "password" -> weblogin.password
			    )
			      webLoginList.add(loginDetails)
			      z(0) = loginDetails
			      return new ResponseEntity[String]((z(0)).toString(), HttpStatus.CREATED);
		}
	  }
	
	@RequestMapping(value=Array("{uid}/weblogins"),method =Array(RequestMethod.GET))
	@ResponseBody
		def viewWebLogins(@PathVariable uid: String) : ResponseEntity[String] = {
		  var list : String = ""
			 for(x <- webLoginList){
				if(x != null) 
				  x.keys.foreach{i => if(i.equalsIgnoreCase("user_id") && x(i).equals(uid))
				    list += x
				}
			 }
		  if(!(list.contains(uid))){
			    return new ResponseEntity[String]("No cards found for the specified user", HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity[String](list, HttpStatus.OK);
			}
		}
	
	@RequestMapping(value=Array("{uid}/weblogins/{lid}"),method =Array(RequestMethod.DELETE))
	@ResponseBody
		def deleteWebLogins(@PathVariable uid : String, @PathVariable lid : String) : ResponseEntity[String] = {
			var status = false;
			var itr = webLoginList.iterator
			while(itr.hasNext()){
			  	var x = itr.next()
			  	x.keys.foreach{
				  	i => if(i.equalsIgnoreCase("login_id") && x(i).equals(lid)){
				  			itr.remove()
				  			status = true 
				  		} 
			  	}  
		}	
			if(status){
		  return new ResponseEntity[String]("Deleted", HttpStatus.NO_CONTENT);
		}else{
		  return new ResponseEntity[String]("login ID does not exist", HttpStatus.NOT_FOUND);
		}
	}
	  
	@RequestMapping(value=Array("{uid}/bankaccounts"),method =Array(RequestMethod.POST))
	@ResponseBody
	def addBankAccounts(@Valid @RequestBody bankaccounts :BankAccount, @PathVariable uid:String) : ResponseEntity[String] = {
	   
	   var userString = userList(uid.toInt - 1)
	   var randomId = randomGenerator.nextInt(1000000)
	   var bank_id : String =  randomId.toString()
		if(userString == null){
			return new ResponseEntity[String]("User ID does not exist", HttpStatus.NOT_FOUND);
		}else{
			bankaccounts.bid = bank_id
			var bankDetails = scala.collection.mutable.Map(
				"user_id" -> uid,
				"bank_id" -> bank_id,
			    "account_name" -> bankaccounts.account_name,
			    "account_number" -> bankaccounts.account_number,	
			    "routing_number" -> bankaccounts.routing_number)
			      bankAccountList.add(bankDetails)
			      z(0) = bankDetails
			      return new ResponseEntity[String]((z(0)).toString(), HttpStatus.CREATED);
		}
	  }
	
	@RequestMapping(value=Array("{uid}/bankaccounts"),method =Array(RequestMethod.GET))
	@ResponseBody
		def viewBankAccounts(@PathVariable uid: String) : ResponseEntity[String] = {
		  var list : String = ""
			 for(x <- bankAccountList){
				if(x != null) 
				  x.keys.foreach{i => if(i.equalsIgnoreCase("user_id") && x(i).equals(uid))
				    list += x
				}
			 }
		  if(!(list.contains(uid))){
			    return new ResponseEntity[String]("No cards found for the specified user", HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity[String](list, HttpStatus.OK);
			}
		}
	
	@RequestMapping(value=Array("{uid}/bankaccounts/{bid}"),method =Array(RequestMethod.DELETE))
	@ResponseBody
		def deleteBankAccounts(@PathVariable uid : String, @PathVariable bid : String) : ResponseEntity[String] = {
			var status = false;
			var itr = bankAccountList.iterator
			while(itr.hasNext()){
			  	var x = itr.next()
			  	x.keys.foreach{
				  	i => if(i.equalsIgnoreCase("bank_id") && x(i).equals(bid)){
				  			itr.remove()
				  			status = true 
				  		} 
			  	}  
		}	
			if(status){
		  return new ResponseEntity[String]("Deleted", HttpStatus.NO_CONTENT);
		}else{
		  return new ResponseEntity[String]("Bank Account ID does not exist", HttpStatus.NOT_FOUND);
		}
	}

}