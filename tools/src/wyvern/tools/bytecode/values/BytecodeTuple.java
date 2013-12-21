package wyvern.tools.bytecode.values;

import java.util.ArrayList;
import java.util.List;

public class BytecodeTuple extends AbstractBytecodeValue<List<BytecodeValue>> {

	public BytecodeTuple(List<BytecodeValue> v) {
		super(v);
	}

	@Override
	public BytecodeValue doInvoke(BytecodeValue o, String op) {
		BytecodeTuple operand = (BytecodeTuple) o;
		List<BytecodeValue> tuple = new ArrayList<BytecodeValue>();
		for(int i = 0 ; i < value.size(); i++) {
			tuple.add(value.get(i).doInvoke(operand.value.get(i), op));
		}
		return new BytecodeTuple(tuple);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BytecodeTuple)) {
			return false;
		}
		BytecodeTuple bct = (BytecodeTuple) obj;
		return value.equals(bct.value);
	}
}
