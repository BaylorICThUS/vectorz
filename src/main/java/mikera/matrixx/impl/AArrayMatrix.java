package mikera.matrixx.impl;

import mikera.vectorz.util.ErrorMessages;

/**
 * Abstract class for a Matrix backed with a single double[] data array
 * 
 * Dimensions are fixed, but leaves open the possibility of arbitrary indexing
 * 
 * @author Mike
 *
 */
public abstract class AArrayMatrix extends ARectangularMatrix {
	private static final long serialVersionUID = 7423448070352281717L;

	public final double[] data;

	protected AArrayMatrix(double[] data, int rows, int cols ) {
		super(rows,cols);
		this.data=data;
	}
	
	@Override
	public double get(int i, int j) {
		if ((i<0)||(i>=rows)||(j<0)||(j>=cols)) throw new IndexOutOfBoundsException(ErrorMessages.invalidIndex(this, i,j));
		return data[index(i,j)];
	}
	
	@Override
	public void set(int i, int j,double value) {
		if ((i<0)||(i>=rows)||(j<0)||(j>=cols)) throw new IndexOutOfBoundsException(ErrorMessages.invalidIndex(this, i,j));
		data[index(i,j)]=value;
	}
	
	@Override
	public double unsafeGet(int i, int j) {
		return data[index(i,j)];
	}
	
	@Override
	public void unsafeSet(int i, int j,double value) {
		data[index(i,j)]=value;
	}
	
	/**
	 * Returns true if the data array is fully packed by this matrix in row-major order
	 * @return
	 */
	public abstract boolean isPackedArray();
	
	/**
	 * Computes the index into the data array for a given position in the matrix
	 * @param i
	 * @param j
	 * @return
	 */
	protected abstract int index(int i, int j);

	public double[] getArray() {
		return data;
	}
}
