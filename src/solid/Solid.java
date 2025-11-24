package solid;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solid {

    protected List<Point3D> vertexBuffer = new ArrayList<>();
    protected List<Integer> indexBuffer = new ArrayList<>();


    protected void addIndicies(Integer... indicies) {
        indexBuffer.addAll(Arrays.asList(indicies));
    }

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }
}
