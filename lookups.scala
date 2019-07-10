
// Süleyman Savas, 2016-12-16
// Halmstad University


package fpDivision

import chisel3._


class lookupL() extends Module {
	val io = IO(new Bundle{

		val addr = Input(UInt(width = 6.W))
		val out  = Output(UInt(width = 27.W))
	})

	//Let's prepare the lookup tables
	val coeffL = Vec(
		UInt("b000000000000000000000000000"), UInt("b111111000000111111000000111"), 
		UInt("b111110000011111000001111100"), UInt("b111101001000100110001101010"),
		UInt("b111100001111000011110000111"), UInt("b111011010111001100000011101"),
		UInt("b111010100000111010100000111"), UInt("b111001101100001010110100010"),
		UInt("b111000111000111000111000111"), UInt("b111000000111000000111000000"),
		UInt("b110111010110011111001000101"), UInt("b110110100111010000001101101"),
		UInt("b110101111001010000110101111"), UInt("b110101001100011101111011000"),
		UInt("b110100100000110100100000110"), UInt("b110011110110010001110100101"),

		UInt("b110011001100110011001100110"), UInt("b110010100100010110000111111"),
		UInt("b110001111100111000001100011"), UInt("b110001010110010111001000011"),
		UInt("b110000110000110000110000110"), UInt("b110000001100000011000000110"),
		UInt("b101111101000001011111010000"), UInt("b101111000101001001100100000"),
		UInt("b101110100010111010001011101"), UInt("b101110000001011100000010111"),
		UInt("b101101100000101101100000101"), UInt("b101101000000101101000000101"),
		UInt("b101100100001011001000010110"), UInt("b101100000010110000001011000"),
		UInt("b101011100100110001000001010"), UInt("b101011000111011010010001100"),

		UInt("b101010101010101010101010101"), UInt("b101010001110100000111111010"),
		UInt("b101001110010111100000101001"), UInt("b101001010111111010110101000"),
		UInt("b101000111101011100001010001"), UInt("b101000100011011111000011001"),
		UInt("b101000001010000010100000101"), UInt("b100111110001000101100101111"),
		UInt("b100111011000100111011000100"), UInt("b100111000000100111000000100"),
		UInt("b100110101001000011100111110"), UInt("b100110010001111100011010010"),
		UInt("b100101111011010000100101111"), UInt("b100101100100111111011010011"),
		UInt("b100101001111001000001001010"), UInt("b100100111001101010000101110"),

		UInt("b100100100100100100100100100"), UInt("b100100001111110110111100000"),
		UInt("b100011111011100000100011111"), UInt("b100011100111100000110101011"),
		UInt("b100011010011110111001011000"), UInt("b100011000000100011000000100"),
		UInt("b100010101101100011110010111"), UInt("b100010011010111001000000100"),
		UInt("b100010001000100010001000100"), UInt("b100001110110011110101011010"),
		UInt("b100001100100101110001010011"), UInt("b100001010011010000001000010"),
		UInt("b100001000010000100001000010"), UInt("b100000110001001001101110100"),
		UInt("b100000100000100000100000100"), UInt("b100000010000001000000100000")
	)

//	val coeffL = Mem(Bits(width = 27), 64, seqRead = true)
	val	data = Reg(init = coeffL(io.addr), next = coeffL(io.addr))
	io.out   := data
	//io.out := coeffL(io.addr)
/*	when(io.addr === "b000000".U){io.out := "b000000000000000000000000000".U} // 0
	.elsewhen(io.addr === "b000001".U){io.out := "b111111000000111111000000111".U}
	.elsewhen(io.addr === "b000010".U){io.out := "b111110000011111000001111100".U}
	.elsewhen(io.addr === "b000011".U){io.out := "b111101001000100110001101010".U}
	.elsewhen(io.addr === "b000100".U){io.out := "b111100001111000011110000111".U}
	.elsewhen(io.addr === "b000101".U){io.out := "b111011010111001100000011101".U}
	.elsewhen(io.addr === "b000110".U){io.out := "b111010100000111010100000111".U}
	.elsewhen(io.addr === "b000111".U){io.out := "b111001101100001010110100010".U}

	.elsewhen(io.addr === "b001000".U){io.out := "b111000111000111000111000111".U} // 8
	.elsewhen(io.addr === "b001001".U){io.out := "b111000000111000000111000000".U}
	.elsewhen(io.addr === "b001010".U){io.out := "b110111010110011111001000101".U}
	.elsewhen(io.addr === "b001011".U){io.out := "b110110100111010000001101101".U}
	.elsewhen(io.addr === "b001100".U){io.out := "b110101111001010000110101111".U}
	.elsewhen(io.addr === "b001101".U){io.out := "b110101001100011101111011000".U}
	.elsewhen(io.addr === "b001110".U){io.out := "b110100100000110100100000110".U}
	.elsewhen(io.addr === "b001111".U){io.out := "b110011110110010001110100101".U}

	.elsewhen(io.addr === "b010000".U){io.out := "b000000000000000000000000000".U} //16
	.elsewhen(io.addr === "b010001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b010111".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011000".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b011111".U){io.out := "b000000000000000000000000000".U}

	.elsewhen(io.addr === "b100000".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b100111".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101000".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b101111".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110000".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b110111".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111000".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111001".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111010".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111011".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111100".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111101".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111110".U){io.out := "b000000000000000000000000000".U}
	.elsewhen(io.addr === "b111111".U){io.out := "b000000000000000000000000000".U}
*/

}

class lookupJ() extends Module {
	val io = IO(new Bundle{

		val addr = Input(UInt(width = 6.W))
		val out  = Output(UInt(width = 23.W))
	})

	//Let's prepare the lookup tables
	val coeffJ = Vec(
		UInt("b00000011111111111110000"), UInt("b00000011111000001001111"),
		UInt("b00000011110000101100010"), UInt("b00000011101001100011111"),
		UInt("b00000011100010101111101"), UInt("b00000011011100001110000"),
		UInt("b00000011010101111110010"), UInt("b00000011001111111111010"),
		UInt("b00000011001010010000001"), UInt("b00000011000100101111111"),
		UInt("b00000010111111011101111"), UInt("b00000010111010011001011"),
		UInt("b00000010110101100001100"), UInt("b00000010110000110101110"),
		UInt("b00000010101100010101011"), UInt("b00000010101000000000000"),

		UInt("b00000010100011110100111"), UInt("b00000010011111110011101"),
		UInt("b00000010011011111011110"), UInt("b00000010011000001100110"),
		UInt("b00000010010100100110001"), UInt("b00000010010001000111110"),
		UInt("b00000010001101110001000"), UInt("b00000010001010100001101"),
		UInt("b00000010000111011001010"), UInt("b00000010000100010111101"),
		UInt("b00000010000001011100100"), UInt("b00000001111110100111011"),
		UInt("b00000001111011111000010"), UInt("b00000001111001001110101"),
		UInt("b00000001110110101010100"), UInt("b00000001110100001011011"),

		UInt("b00000001110001110001011"), UInt("b00000001101111011100000"),
		UInt("b00000001101101001011001"), UInt("b00000001101010111110110"),
		UInt("b00000001101000110110100"), UInt("b00000001100110110010010"),
		UInt("b00000001100100110001111"), UInt("b00000001100010110101010"),
		UInt("b00000001100000111100010"), UInt("b00000001011111000110101"),
		UInt("b00000001011101010100011"), UInt("b00000001011011100101010"),
		UInt("b00000001011001111001010"), UInt("b00000001011000010000001"),
		UInt("b00000001010110101001111"), UInt("b00000001010101000110011"),

		UInt("b00000001010011100101101"), UInt("b00000001010010000111011"),
		UInt("b00000001010000101011100"), UInt("b00000001001111010010001"),
		UInt("b00000001001101111011000"), UInt("b00000001001100100110001"),
		UInt("b00000001001011010011011"), UInt("b00000001001010000010110"),
		UInt("b00000001001000110100001"), UInt("b00000001000111100111011"),
		UInt("b00000001000110011100101"), UInt("b00000001000101010011101"),
		UInt("b00000001000100001100011"), UInt("b00000001000011000110110"),
		UInt("b00000001000010000010111"), UInt("b00000001000001000000101")
	)

	io.out := coeffJ(io.addr)
}

class lookupC() extends Module {
	val io = IO(new Bundle{

		val addr = Input(UInt(width = 6.W))
		val out  = Output(UInt(width = 24.W))
	})

	//Let's prepare the lookup tables
	val coeffC = Vec(
		UInt("b000000000000111110100001"), UInt("b000000000000111011101101"),
		UInt("b000000000000111001000011"), UInt("b000000000000110110100011"),
		UInt("b000000000000110100001100"), UInt("b000000000000110001111110"),
		UInt("b000000000000101111111000"), UInt("b000000000000101101111001"),
		UInt("b000000000000101100000001"), UInt("b000000000000101010010000"),
		UInt("b000000000000101000100100"), UInt("b000000000000100110111111"),
		UInt("b000000000000100101011110"), UInt("b000000000000100100000010"),
		UInt("b000000000000100010101011"), UInt("b000000000000100001011001"),

		UInt("b000000000000100000001010"), UInt("b000000000000011110111111"),
		UInt("b000000000000011101111000"), UInt("b000000000000011100110100"),
		UInt("b000000000000011011110011"), UInt("b000000000000011010110101"),
		UInt("b000000000000011001111011"), UInt("b000000000000011001000010"),
		UInt("b000000000000011000001101"), UInt("b000000000000010111011001"),
		UInt("b000000000000010110101000"), UInt("b000000000000010101111001"),
		UInt("b000000000000010101001100"), UInt("b000000000000010100100001"),
		UInt("b000000000000010011111000"), UInt("b000000000000010011010000"),

		UInt("b000000000000010010101010"), UInt("b000000000000010010000110"),
		UInt("b000000000000010001100011"), UInt("b000000000000010001000010"),
		UInt("b000000000000010000100001"), UInt("b000000000000010000000010"),
		UInt("b000000000000001111100101"), UInt("b000000000000001111001000"),
		UInt("b000000000000001110101100"), UInt("b000000000000001110010010"),
		UInt("b000000000000001101111000"), UInt("b000000000000001101100000"),
		UInt("b000000000000001101001000"), UInt("b000000000000001100110001"),
		UInt("b000000000000001100011011"), UInt("b000000000000001100000110"),

		UInt("b000000000000001011110010"), UInt("b000000000000001011011110"),
		UInt("b000000000000001011001011"), UInt("b000000000000001010111000"),
		UInt("b000000000000001010100111"), UInt("b000000000000001010010101"),
		UInt("b000000000000001010000101"), UInt("b000000000000001001110101"),
		UInt("b000000000000001001100101"), UInt("b000000000000001001010110"),
		UInt("b000000000000001001001000"), UInt("b000000000000001000111010"),
		UInt("b000000000000001000101100"), UInt("b000000000000001000011111"),
		UInt("b000000000000001000010010"), UInt("b000000000000001000000110")
	)

	io.out := coeffC(io.addr)
}
