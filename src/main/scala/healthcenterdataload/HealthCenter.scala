package healthcenterdataload

import java.sql.{Connection,DriverManager}

import healthcenterdataload.Model._
import javax.persistence._

import org.json4s.native.JsonMethods._
import org.json4s.{DefaultFormats, _}

import scala.io.Source._

case class DataToStoreClass(
  name_2 : Option[String],
  name_1 : Option[String],
  flag_md : Option[String],
  city : Option[String],
  latitude : Option[String],
  flag_mc : Option[String],
  flag_hv : Option[String],
  flag_gl : Option[String],
  longitude : Option[String],
  zip : Option[String],
  flag_pi : Option[String],
  street_1 : Option[String],
  phone : Option[String],
  flag_yad : Option[String])

object DataToStore extends App{

  // This is hard-coded right now, but could be moved out to a property file if needed
  // so it could be changed on the fly.
  val url1 = "https://data.cityofnewyork.us/resource/8nqg-ia7v.json"

  def storeData(url: String): Unit = {
    // This is needed for date/time formatting with the json4s library
    implicit val formats = DefaultFormats

    val data = fromURL(url).mkString
    val dataParsedToList = parse(data).extract[List[DataToStoreClass]]

    val dataToStore = new HealthCenterData


    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcenters", "root", "root")

      dataParsedToList.foreach { facility =>

        val alreadyStoredData = em.createNamedQuery("findExistingHealthCenters")
          .setParameter("name2", facility.name_2.getOrElse(""))
          .setParameter("name1", facility.name_1.getOrElse(""))
          .getResultList

        // Only store data if it's not already in the database.
        // I realize starting by checking for a false here is a little hard to read, but it's the
        // way to do it with as few lines as possible

        try{
          if( !alreadyStoredData.contains(facility.name_1.getOrElse("")) || !alreadyStoredData.contains(facility.name_2.getOrElse("")) ) {
            dataToStore.name1 = facility.name_1.getOrElse("")
            dataToStore.name2 = facility.name_2.getOrElse("")
            dataToStore.city = facility.city.getOrElse("")
            dataToStore.latitude = facility.latitude.getOrElse("")
            dataToStore.longitude = facility.longitude.getOrElse("")
            dataToStore.zip = facility.zip.getOrElse("")
            dataToStore.street1 = facility.street_1.getOrElse("")
            dataToStore.phone = facility.phone.getOrElse("")

            em.persist(dataToStore)
            em.flush
            //con.commit()
            //con.close()
            alreadyStoredData.clear
          }
        } catch {
          case e: Exception => e.printStackTrace()
        } finally { con.close}
      }
  }

}

@Entity
@Table(name="healthcare_center_data")
@NamedQuery(name="findExistingHealthCenters",
  query="SELECT hcd from HealthCenterData hcd where hcd._name2 = :name2 and hcd._name1 = :name1")
class HealthCenterData{

  @Id
  @Column(name="id")
  private var _id : Int = _
  def id = _id

  @Column(name="name1")
  private var _name1 : String = ""
  def name1 = _name1
  def name1_= (value: String) = _name1 = value

  @Column(name="name2")
  private var _name2 : String = ""
  def name2 = _name2
  def name2_= (value: String) = _name2 = value

  @Column(name="city")
  private var _city : String = ""
  def city = _city
  def city_= (value: String) = _city = value

  @Column(name="street1")
  private var _street1 : String = ""
  def street1 = _street1
  def street1_= (value: String) = _street1 = value

  @Column(name="zip")
  private var _zip : String = ""
  def zip = _zip
  def zip_= (value: String) = _zip = value

  @Column(name="latitude")
  private var _latitude : String = ""
  def latitude = _latitude
  def latitude_= (value: String) = _latitude = value

  @Column(name="longitude")
  private var _longitude : String = ""
  def longitude = _longitude
  def longitude_= (value: String) = _longitude = value

  @Column(name="phone")
  private var _phone : String = ""
  def phone = _phone
  def phone_= (value: String) = _phone = value

}