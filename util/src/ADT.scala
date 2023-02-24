package sustainability
package util

object ADT {
  case class PetCategory(id: Int, name: String)
  case class PetTag(id: Int, name: String)
  case class Pet(id: Int, name: String, category: PetCategory, photoUrls: Vector[String], vacinatted: Boolean, tags: Vector[PetTag])
  case class PetStore(name: String, address: String, latitude: Double, longitude: Double, pets: Vector[Pet])
}
