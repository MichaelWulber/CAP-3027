package Mesh;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class LeafMesh {
    private final int resolution = 15;
    private final int thickness = 2;

    private TriangleMesh mesh;
    private Color color;
    private double r1;
    private double r2;
    private double tilt;

    public LeafMesh(Color color, double r1, double r2, double tilt){
        this.color = color;
        this.r1 = r1;
        this.r2 = -r2;
        this.tilt = Math.toRadians(tilt);

        this.mesh = new TriangleMesh();
        genVertices();
        setTexture();
        genFaces();
    }

    public LeafMesh(LeafDescription ld){
        this.color = ld.color;
        this.r1 = ld.r1;
        this.r2 = -ld.r2;
        this.tilt = Math.toRadians(ld.tilt);

        this.mesh = new TriangleMesh();
        genVertices();
        setTexture();
        genFaces();
    }

    public void genVertices(){
        double dt = (2.0 * Math.PI)/(double) resolution;

        float xx = 0;
        float yy = 0;
        float zz = 0;

        float x = 0;
        float y = 0;
        float z = 0;

        for (int r = 0; r < resolution; ++r) {
            xx = (float) (r1 * Math.cos(r * dt));
            yy = (float) (r2 * Math.sin(r * dt) + r2);
            zz = 0;

            x = xx;
            y = yy;
            z = zz;

            yy = (float) (Math.cos(tilt) * y + Math.sin(tilt) * z);
            zz = (float) (-Math.sin(tilt) * y + Math.cos(tilt) * z);

            mesh.getPoints().addAll(xx, yy, zz);
        }

        for (int r = 0; r < resolution; ++r) {
            xx = (float) (r1 * Math.cos(r * dt));
            yy = (float) (r2 * Math.sin(r * dt) + r2);
            zz = thickness;

            x = xx;
            y = yy;
            z = zz;

            yy = (float) (Math.cos(tilt) * y + Math.sin(tilt) * z);
            zz = (float) (-Math.sin(tilt) * y + Math.cos(tilt) * z);

            mesh.getPoints().addAll(xx, yy, zz);
        }

        mesh.getPoints().addAll(0, (float) (Math.cos(tilt) * r2), (float) (-Math.sin(tilt) * r2),
                0, (float) (Math.cos(tilt) * r2), (float) (-Math.sin(tilt) * r2 + Math.cos(tilt) * thickness));
    }

    public void setTexture(){
        this.mesh.getTexCoords().addAll(0,0);
    }

    public void genFaces(){
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

    public TriangleMesh getMesh(){
        return mesh;
    }

    public MeshView getMeshView(){
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(color);
        material.setDiffuseColor(color);

        MeshView meshy = new MeshView(mesh);
        meshy.setMaterial(material);
        meshy.setDrawMode(DrawMode.FILL);
        return meshy;
    }
}
