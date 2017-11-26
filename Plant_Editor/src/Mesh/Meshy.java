package Mesh;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Meshy {
    private TriangleMesh mesh;

    private int resolution;
    private Color color;

    private double[][] skeleton;
    private double[] radii;

    public Meshy(double[][] skeleton, double[][] angles, double[] radii, int resolution, Color color){
        this.resolution = resolution;
        this.radii = radii;
        this.skeleton = skeleton;

        this.color = color;

        this.mesh = new TriangleMesh();
        genVertices(skeleton, angles);
        setTexture();
        genFaces();
    }

    private void genVertices(double[][] skeleton, double[][] angles){
        double dt = (2.0 * Math.PI)/(double) resolution;
        float theta = 0;
        float phi = 0;

        float xx = 0;
        float yy = 0;
        float zz = 0;

        float x;
        float y;
        float z;

        for (int i = 0; i < skeleton.length; ++i){
            theta = (float) (angles[i][0] * Math.PI/180.0);
            phi = (float) (angles[i][1] * Math.PI/180.0);
            for (int r = 0; r < resolution; ++r){
                // calculate offset
                xx = (float) (radii[i] * Math.cos(r * dt));
                yy = 0;
                zz = (float) (radii[i] * Math.sin(r * dt));

                x = xx;
                y = yy;
                z = zz;

                // rotate vertex around x-axis by theta
                // xx doesn't change
                yy = (float) (Math.cos(theta) * y + Math.sin(theta) * z);
                zz = (float) (-Math.sin(theta) * y + Math.cos(theta) * z);

                x = xx;
                y = yy;
                z = zz;

                // rotate vertex around z-axis by phi
                xx = (float) (Math.cos(phi) * x + Math.sin(phi) * y);
                yy = (float) (-Math.sin(phi) * x + Math.cos(phi) * y);
                // zz doesn't change

                // add offset to base
                xx += (float) skeleton[i][0];
                yy += (float) skeleton[i][1];
                zz += (float) skeleton[i][2];

                // add vertices to mesh object
                mesh.getPoints().addAll(xx, yy, zz);
            }
        }

        // add top and bottom center points
        mesh.getPoints().addAll((float)skeleton[0][0], (float)skeleton[0][1], (float)skeleton[0][2],
                (float)skeleton[skeleton.length - 1][0], (float)skeleton[skeleton.length - 1][1], (float)skeleton[skeleton.length - 1][2]);
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

        System.out.println("face values");
        for (int i = 0; i < skeleton.length - 1; ++i){
            base1 = i * resolution;
            base2 = (i + 1) * resolution;
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
        }

        // bottom faces
        for (int i = 0; i < resolution; ++i) {
            mesh.getFaces().addAll(i,0,
                    resolution * skeleton.length,0,
                    (i + 1)%resolution,0);
        }

        // top faces
        for (int i = 0; i < resolution; ++i) {
            mesh.getFaces().addAll(
                    resolution * skeleton.length + 1,0,
                    resolution * (skeleton.length - 1) + i,0,
                    resolution * (skeleton.length - 1) + (i + 1)%resolution,0);
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

