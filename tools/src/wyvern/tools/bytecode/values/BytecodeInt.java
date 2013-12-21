package wyvern.tools.bytecode.values;

public class BytecodeInt extends AbstractBytecodeValue<Integer> {

	public BytecodeInt(int v) {
		super(v);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BytecodeInt)) {
			return false;
		}
		BytecodeInt bci = (BytecodeInt) obj;
		return value.equals(bci.value);
	}

	@Override
	public BytecodeValue doInvoke(BytecodeValue o, String op) {
		BytecodeInt operand = (BytecodeInt) o;
		switch (op) {
		case "+":
			return new BytecodeInt(value + operand.value);
		case "-":
			return new BytecodeInt(value - operand.value);
		case "*":
			return new BytecodeInt(value * operand.value);
		case "/":
			return new BytecodeInt(value / operand.value);
		
		// TODO temporary line: to be rewritten
		default: throw new RuntimeException("Bad arithmetic operation " + op);
		}
	}
}
