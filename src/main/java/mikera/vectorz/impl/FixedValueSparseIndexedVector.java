package mikera.vectorz.impl;

import java.io.Serializable;
import java.util.Arrays;

import mikera.indexz.Index;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.impl.ADenseArrayVector;
import mikera.vectorz.impl.ASparseIndexedVector;
import mikera.vectorz.impl.ASparseVector;

public class FixedValueSparseIndexedVector extends ASparseIndexedVector{

    private Index index;
	public FixedValueSparseIndexedVector(int length, Index index) {
		super(length);
		this.index = index;
    }

	@Override
	public void add(ASparseVector v) {
	    throw new UnsupportedOperationException("add ASparseVector to FixedValueSparseIndexedVector op is not supported!");
    }
    @Override
	public int nonSparseElementCount(){
		return index.length();
	}
	@Override
	public Index nonSparseIndex() {
		return index;
	}
	@Override
	public Vector nonSparseValues() {
		double[] arr= new double[index.length()];
		Arrays.fill(arr,1.0);
		return Vector.wrap(arr);
	}
	@Override
	public FixedValueSparseIndexedVector exactClone() {
		return new FixedValueSparseIndexedVector(length, index.clone());
	}
	@Override

	public void set(int i, double value) {
		throw new UnsupportedOperationException("set is not supported in FixedValueSparseIndexedVector!");
	}


    @Override
	double[] internalData(){
        double[] arr= new double[index.length()];
		Arrays.fill(arr,1.0);
		return arr;
	}
	@Override
	public double get(int i) {
		checkIndex(i);
		int ip = index.indexPosition(i);
		if (ip < 0)
			return 0.0;
		//fixed value 1.0 for all elements
		return 1.0;
	}
	Index internalIndex(){
		return index;
	}
    public double dotProduct(AVector v) {
		//if (v instanceof ADenseArrayVector) return dotProduct((ADenseArrayVector)v);
		//if (v instanceof ASparseVector) return dotProduct((ASparseVector)v);

		// for fixed value sparse indexed vector
		double result=0.0;
		int length = index.length();
		int[] ixs=internalIndex().data;
		for (int j=0; j<length; j++) {
			result+=v.unsafeGet(ixs[j]);
		}
		return result;
   }
}
