/**
  * Script to get data from the json and add new health centers to the database
  */

import healthcenterdataload._

val url = "https://data.cityofnewyork.us/resource/8nqg-ia7v.json"

try {
  DataToStore.storeData(url)
} catch {
  case e: Exception => throw new Exception("The health center update script failed to run.")
}