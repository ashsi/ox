object Cipher{
  /** Bit-wise exclusive-or of two characters */
  def xor(a: Char, b: Char) : Char = (a.toInt ^ b.toInt).toChar

  /** Print ciphertext in octal */
  def showCipher(cipher: Array[Char]) =
    for(c <- cipher){ print(c/64); print(c%64/8); print(c%8); print(" ") }

  /** Read file into array */
  def readFile(fname: String) : Array[Char] = 
    scala.io.Source.fromFile(fname).toArray

  /** Read from stdin in a similar manner */
  def readStdin() = scala.io.Source.stdin.toArray

  /* ----- Functions below here need to be implemented ----- */

  /** Encrypt plain using key; can also be used for decryption */
  def encrypt(key: Array[Char], plain: Array[Char]) : Array[Char] = {
    val cipher = new Array[Char](plain.size)
    var i = 0
    while(i < plain.size) {
      //applies xor to each char in plain while key chars cycle through
      cipher(i) = xor(plain(i), key(i % key.size))
      i += 1
    }
    cipher
  }

  /** Try to decrypt ciphertext, using crib as a crib */
  def tryCrib(crib: Array[Char], ciphertext: Array[Char]) = {
    var start = 0
    val K = crib.length
    val n = ciphertext.length
    var trying = true
    while(start <= n-K-1 && trying) {
      //cycle through increasing starting positions of the crib
      var i = 0
      var j = 1
      val text = new Array[Char](K)
      //text takes K chars from ciphertext starting with ciphertext(start)
      while(i < K) {
        text(i) = ciphertext(start+i)
        i += 1
      }
      //work out keyChars for the crib at start position
      val keyChars = encrypt(text, crib)
      //work out whether there is a repetition (i.e. if there is a key) of length j
      while(j <= K-2 && trying) {
        if(repetition(keyChars, j, K)) {          
          //println(j)
          trying = false
        } else j += 1 
      }
      //if a repitition is found (so j = correct key length)
      //the key is made by taking elements from keyChars
      if(trying == false) {
        val key = new Array[Char](j)
        var k = 0
        //adjust is a constant to get the key characters in the order as it is at the start of ciphertext
        //this is necessary because the crib may not occur at the start of ciphertext
        val adjust = (start/j)*j-start%j 
        //test: println("keyChars:"+ new String (keyChars))
        //test: println("adjust:" +adjust)
        while(k < j) {
          //k iterates by one to assign each successive element of the key
          //adjust is a mod function
          key(k) = keyChars((k+adjust)%j)
          k += 1
        }
        //prints the correct key and the decrypted message
        println(new String (key))
        println(new String (encrypt(key, ciphertext)))
      }
      else start += 1
    }
  }

  def repetition(keyChars: Array[Char], j: Int, K: Int) : Boolean = {
    var i = 0
    var l = j
    var repeats = true
    //checks that two elements of keyChars with an interval of length j are the same
    //if they are the same up until the end of keyChars (keyChars.length = K) then the key is of length j
    while(l < K && i < K-j && repeats == true) {
      if(keyChars(i) == keyChars(l)) {
        repeats = true
        i += 1
        l += 1
      } else {
        repeats = false
      }
    }
    repeats
  }

  /** The first optional statistical test, to guess the length of the key */
  def crackKeyLen(ciphertext: Array[Char]) = ???

  /** The second optional statistical test, to guess characters of the key. */
  def crackKey(klen: Int, ciphertext: Array[Char]) = ???

/** The main method just selects which piece of functionality to run */
  def main(args: Array[String]) = {
    // string to print if error occurs
    val errString = 
      "Usage: scala Cipher (-encrypt|-decrypt) key [file]\n"+
      "     | scala Cipher -crib crib [file]\n"+
      "     | scala Cipher -crackKeyLen [file]\n"+
      "     | scala Cipher -crackKey len [file]"

    // Get the plaintext, either from the file whose name appears in position
    // pos, or from standard input
    def getPlain(pos: Int) = 
      if(args.length==pos+1) readFile(args(pos)) else readStdin

    // Check there are at least n arguments
    def checkNumArgs(n: Int) = if(args.length < n){println(errString); sys.exit}

    // Parse the arguments, and call the appropriate function
    checkNumArgs(1)
    val command = args(0)
    if(command=="-encrypt" || command=="-decrypt"){
      checkNumArgs(2); val key = args(1).toArray; val plain = getPlain(2)
      print(new String (encrypt(key,plain)))
    }
    else if(command=="-crib"){
      checkNumArgs(2); val key = args(1).toArray; val plain = getPlain(2)
      tryCrib(key, plain)
    }
    else if(command=="-crackKeyLen"){
      checkNumArgs(1); val plain = getPlain(1)
      crackKeyLen(plain)
    }      
    else if(command=="-crackKey"){
      checkNumArgs(2); val klen = args(1).toInt; val plain = getPlain(2)
      crackKey(klen, plain)
    }
    else println(errString)
  }
}
