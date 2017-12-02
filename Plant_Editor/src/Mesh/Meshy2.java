package Mesh;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Meshy2 {
    private TriangleMesh mesh;
    private int resolution;
    private Color color;
    private float height;
    private float radius;

    public Meshy2(float height, float radius, int resolution, Color color){
        this.mesh = new TriangleMesh();
        this.resolution = resolution;
        this.height = height;
        this.radius = radius;
        this.color = color;
        genVertices();
        setTexture();
        genFaces();
    }

    private void genVertices(){
        double dt = (2.0 * Math.PI)/(double) resolution;

        float xx = 0;
        float yy = 0;
        float zz = 0;

        for (int r = 0; r < resolution; ++r) {
            xx = (float) (radius * Math.cos(r * dt));
            yy = 0;
            zz = (float) (radius * Math.sin(r * dt));
            mesh.getPoints().addAll(xx, yy, zz);
        }

        for (int r = 0; r < resolution; ++r) {
            xx = (float) (radius * Math.cos(r * dt));
            yy = height;
            zz = (float) (radius * Math.sin(r * dt));
            mesh.getPoints().addAll(xx, yy, zz);
        }

        mesh.getPoints().addAll(0, 0, 0, 0, height, 0);
    }

    private void setTexture(){
        this.mesh.getTexCoords().addAll(0,0); // no texture ... yet
    }

    private void genFaces(){
        // side faces
        int base1 = 0;
        int base2 = 0;
        int a = 0;
        int b = 0;
        int c = 0;

        base1 = 0;
        base2 = resolution;
        for (int j = 0; j < resolution; ++j){
            // face group 1
            a = base1 + j;
            b = base1 + (j + 1) % resolution;
            c = base2 + (j + 1) % resolution;
            mesh.getFaces().addAll(a,0, b,0, c,0);

            // face group 2
            a = base2 + j;
            b = base1 + j;
            c = base2 + (j + 1) % resolution;
            mesh.getFaces().addAll(a,0, b,0, c,0);
        }

        // bottom faces
        for (int i = 0; i < resolution; ++i) {
            mesh.getFaces().addAll(i,0,
                    resolution * 2,0,
                    (i + 1)%resolution,0);
        }

        // top faces
        for (int i = 0; i < resolution; ++i) {
            mesh.getFaces().addAll(
                    resolution * 2 + 1,0,
                    resolution + i,0,
                    resolution + (i + 1)%resolution,0);
        }
    }

    public TriangleMesh getMesh() {
        return mesh;
    }

    public MeshView getMeshView() {
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(color);
        material.setDiffuseColor(color);

        MeshView meshy = new MeshView(mesh);
        meshy.setMaterial(material);
        meshy.setDrawMode(DrawMode.FILL);
        return meshy;
    }
}
