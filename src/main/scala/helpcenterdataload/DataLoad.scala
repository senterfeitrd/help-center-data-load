package dataload

import dataload.Model._

import javax.persistence._

import org.json4s.native.JsonMethods._
import org.json4s.{DefaultFormats, _}

import scala.io.Source._

case class DataLoad(
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

object DataLoad{

  // This is needed for date/time formatting with the json4s library
  implicit val formats = DefaultFormats

  // This is hard-coded right now, but could be moved out to a property file if needed
  // so it could be changed on the fly.
  val url = "https://data.cityofnewyork.us/resource/8nqg-ia7v.json"

  def find = {

  }

  def storeData(url: String): Unit = {
    val data = fromURL(url).mkString
    val dataParsedToList = parse(data).extract[List[DataLoad]]

    val dataToStore = new HelpCenterData
    try {
      dataParsedToList.foreach { facility =>
        dataToStore.name1    = facility.name_1.getOrElse("")
        dataToStore.name2    = facility.name_2.getOrElse("")
        dataToStore.city      = facility.city.getOrElse("")
        dataToStore.latitude  = facility.latitude.getOrElse("")
        dataToStore.longitude = facility.longitude.getOrElse("")
        dataToStore.zip       = facility.zip.getOrElse("")
        dataToStore.street1  = facility.street_1.getOrElse("")
        dataToStore.phone     = facility.phone.getOrElse("")

        em.persist(dataToStore)
      }
    } catch {
      case e: Exception => throw new Exception("Something went wrong storing data")
    }
  }

}

@Entity
@Table(name = "help_center_data")
class HelpCenterData{

  @Id
  @Column(name="id")
  val _id : Int = _
  def id = _id

  @Column(name="name1")
  var _name1 : String = ""
  def name1 = _name1
  def name1_= (value: String) = _name1 = value

  @Column(name="name2")
  var _name2 : String = ""
  def name2 = _name2
  def name2_= (value: String) = _name2 = value

  @Column(name="active")
  var _active: String = ""
  def active = _active
  def active_= (value: String) = _active = value

  @Column(name="city")
  var _city : String = ""
  def city = _city
  def city_= (value: String) = _city = value

  @Column(name="street1")
  var _street1 : String = ""
  def street1 = _street1
  def street1_= (value: String) = _street1 = value

  @Column(name="zip")
  var _zip : String = ""
  def zip = _zip
  def zip_= (value: String) = _zip = value

  @Column(name="latitude")
  var _latitude : String = ""
  def latitude = _latitude
  def latitude_= (value: String) = _latitude = value

  @Column(name="longitude")
  var _longitude : String = ""
  def longitude = _longitude
  def longitude_= (value: String) = _longitude = value

  @Column(name="phone")
  var _phone : String = ""
  def phone = _phone
  def phone_= (value: String) = _phone = value

}