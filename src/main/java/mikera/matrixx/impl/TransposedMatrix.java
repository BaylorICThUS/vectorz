package mikera.matrixx.impl;

import mikera.matrixx.AMatrix;
import mikera.vectorz.AVector;

/**
 * Class representing a transposed view of another matrix
 * The transposed matrix is a reference to the underlying matrix data
 * 
 * @author Mike
 *
 */
public class TransposedMatrix extends DelegatedMatrix {
	
	private TransposedMatrix(AMatrix source) {
		super(source);
	}
	
	public static AMatrix wrap(AMatrix m) {
		if (m instanceof TransposedMatrix) return ((TransposedMatrix)m).source;
		return new TransposedMatrix(m);
	}

	public int rowCount() {
		return source.columnCount();
	}

	@Override
	public int columnCount() {
		return source.rowCount();
	}

	@Override
	public double get(int row, int column) {
		return source.get(column, row);
	}

	@Override
	public void set(int row, int column, double value) {
		source.set(column,row,value);
	}
	
	@Override
	public AVector getRow(int row) {
		return source.getColumn(row);
	}
	
	@Override
	public AVector slice (int rowNumber) {
		return source.getColumn(rowNumber);
	}
	
	@Override
	public int sliceCount() {
		return source.columnCount();
	}
	
	@Override
	public AVector getColumn(int column) {
		return source.getRow(column);
	}
	
	@Override 
	public double determinant() {
		return source.determinant();
	}
	
	@Override 
	public AMatrix getTranspose() {
		// Transposing again just gets us back to the original source matrix
		return source;
	}
	
	@Override
	public TransposedMatrix exactClone() {
		return new TransposedMatrix(source.exactClone());
	}
}
