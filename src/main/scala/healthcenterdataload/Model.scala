package healthcenterdataload

import javax.persistence._

import java.sql.{Connection,DriverManager}

object Model {

  def fullTxn(oper: => Unit) = ???

  def emf: EntityManagerFactory = Persistence.createEntityManagerFactory("healthcenters")

  def em: EntityManager = emf.createEntityManager

}