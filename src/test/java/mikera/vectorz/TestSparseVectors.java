package mikera.vectorz;

import java.util.Arrays;

import static org.junit.Assert.*;
import mikera.vectorz.impl.SparseHashedVector;
import mikera.vectorz.impl.SparseIndexedVector;
import mikera.vectorz.impl.ZeroVector;
import mikera.vectorz.impl.ASparseVector;
import mikera.vectorz.impl.FixedValueSparseIndexedVector;

import mikera.indexz.Index;

import org.junit.Test;

public class TestSparseVectors {

	@Test 
	public void testHashed() {
		SparseHashedVector v=SparseHashedVector.createLength(10);
		assertEquals(0,v.nonZeroCount());

		v.set(1,1);
		assertEquals(1.0,v.elementSum(),0.0);
		assertEquals(1,v.nonZeroCount());
		
	}
	@Test
	public void testFixedValueSparseIndexedVector(){
		Index id = Index.create(new int[]{1,4,5,6,9});
		FixedValueSparseIndexedVector fviv = new FixedValueSparseIndexedVector(99,id);
		assertEquals(5,fviv.nonZeroCount());
		assertEquals(5.0,fviv.elementSum(),0.0);
		AVector v = Vector.of(1, 1,1,1,2, 3, 4,1,1,2, 5);
		assertEquals(12,fviv.dotProduct(v),0.0);

	}
	@Test 
	public void testIndexed() {
		SparseIndexedVector v=SparseIndexedVector.createLength(10);
		
		assertEquals(0,v.nonZeroCount());

		v.set(1,1);
		assertEquals(1.0,v.elementSum(),0.0);
		assertEquals(1,v.nonZeroCount());
        assertTrue(Arrays.equals(new int[]{1},v.nonZeroIndices()));

        SparseIndexedVector w=v.clone();
        v.add(ZeroVector.create(10));
        assertEquals(w, v);

        SparseIndexedVector empty=SparseIndexedVector.createLength(3);
        SparseIndexedVector nonEmpty=SparseIndexedVector.create(Vector.of(1,0,2));
        empty.add(nonEmpty);
        assertEquals(Vector.of(1,0,2), empty);
	}
	
	@Test
	public void testSpareIndexedCreate() {
		// regression test for basic bug with sparse matrix operations (vectorz-clj #52)
		Vector v=Vector.of(0,0,0,0,0,0,1,0,0,0,0,0,1);
		AVector sv=SparseIndexedVector.createWithIndices(v, v.nonZeroIndices());
		assertEquals(v,sv);
	}

    @Test
    public void testRoundToZero() {
        ASparseVector x=SparseIndexedVector.create(Vector.of(0.01,0.01,2,0)).roundToZero(0.1);
        ASparseVector y=SparseIndexedVector.create(Vector.of(1,0.01,0,2)).roundToZero(0.1);
        x.add(y);
        assertEquals(Vector.of(1,0,2,2), x);

        ASparseVector smaller=SparseIndexedVector.create(Vector.of(1, 0.1, 0.01, 0.001, 0.0001));
        assertEquals(5, smaller.nonZeroCount());
        assertTrue(Arrays.equals(new int[]{0,1,2,3,4}, smaller.nonZeroIndices()));

        smaller=smaller.roundToZero(0.01);
        assertEquals(2, smaller.nonZeroCount());
        assertTrue(Arrays.equals(new int[]{0,1}, smaller.nonZeroIndices()));
    }
}
