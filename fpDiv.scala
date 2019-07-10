
package fpDivision

import chisel3._
import chisel3.util._
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class fpDiv(val w : Int) extends Module{ //w = 32
	val io = IO(new Bundle{
		val in1  = Input(UInt(width = w.W))	
		val in2  = Input(UInt(width = w.W))
		val out  = Output(UInt(width = w.W))
	})

	val inverter   = Module(new fpInverter(23))
	val multiplier = Module(new FPMult(w))

	inverter.io.in1 := io.in2(22, 0)	//mantissa 
	//val exponent2    = io.in2(30, 23) - 127.U
	//val negExpTmp    = 127.U - exponent2		// invert the sign of exponent
	val negExpTmp    = 254.U - io.in2(30, 23)
	val invMant      = inverter.io.out(23, 0)
	val negExp       = Mux(invMant === 0.U, negExpTmp, negExpTmp - 1.U)

	// we should raise an execption if both mantissa and exponent are zero (the final result should be inf

	//val in1Buffer    = Reg(init = 0.U, next = io.in1)
	
	multiplier.io.a := io.in1
	multiplier.io.b := Cat(io.in2(31), negExp, inverter.io.out(23,1))
	// skipping msb of inverter (multiplying mantissa by 2)
	
	io.out := multiplier.io.res
	
	printf("\nmultiplier b : %d\n", Cat(io.in2(31), negExp, inverter.io.out(23,1)))
	//printf("exponent2: %d\n", exponent2)
	printf("negExp : %d, in1 exp %d\n", negExp, io.in1(30, 23))
	printf("in1: %d, in2: %d\n", io.in1, io.in2)
	printf("inverter in: %d out: %d\n", io.in2(22, 0), inverter.io.out)
	printf("out: %d\n", multiplier.io.res)
	
//	printf("%d\n", multiplier.io.res)

}

class fpDivTest(c: fpDiv) extends PeekPokeTester(c) {
/*
	poke(c.io.in1, 1096155136.U)		// 13.75
	poke(c.io.in2, 1091829760.U)		// 9.25

	step(1)
	expect(c.io.out, 1123512320.U)		//123.1875
	step(1)
*/

	poke(c.io.in1, 1065353216.U)	//65536
	poke(c.io.in2, 0.U)	//0
	step(1)
	expect(c.io.out, 0.U)

	poke(c.io.in1, 2139095039.U)
	poke(c.io.in2, 2139095039.U)	
	
	step(1)
	expect(c.io.out, 0.U)

	poke(c.io.in1, 1199570944.U)	//65536
	poke(c.io.in2, 1106771968.U)	//31
	step(1)
	expect(c.io.out, 0.U)

	//poke(c.io.in2, 1098383360.U) // 15.5
	poke(c.io.in1, 2139095039.U)	// biggest single precision float
	poke(c.io.in2, 1207435264.U)
	step(1)
	expect(c.io.out, 0.U)

	poke(c.io.in1, 2139095039.U)
	poke(c.io.in2, 1200035266.U)	
	
	step(1)
	expect(c.io.out, 0.U)

	poke(c.io.in1, 1082130431.U)	// exp = 1, mantissa = 28 ones
	poke(c.io.in2, 1106771968.U)	// 31

	step(1)
	expect(c.io.out, 0.U)
	poke(c.io.in1, 1207435265.U)
	poke(c.io.in2, 2139095039.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	poke(c.io.in1, 1207959552.U)
//	poke(c.io.in1, 1200035266.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)

	//poke(c.io.in2, 0.U)

// 1125056512 = 143.0

// 1199570944 = 65536
// 1207959552 = 131072
// 1106771968 = 31
/*
	for (i <- 1199570944 until 1207959552){
//	for (i <- 1199570944 until 1200095232){
		poke(c.io.in1, i)
		poke(c.io.in2, 1106771968.U)
		step(1)

	}
*/


	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)
	step(1)
	expect(c.io.out, 0.U)

}

object fpDiv {
  def main(args: Array[String]): Unit = {
    if (!Driver(() => new fpDiv(32))(c => new fpDivTest(c))) System.exit(1)
  }
}











