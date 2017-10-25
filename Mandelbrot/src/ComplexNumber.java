public class ComplexNumber {
    public double real;
    public double imag;

    public ComplexNumber(double real, double imag){
        this.real = real;
        this.imag = imag;
    }

    public void add(ComplexNumber num){
        this.real += num.real;
        this.imag += num.imag;
    }

    public void mult(ComplexNumber num){
        double temp = real;
        this.real = this.real*num.real - this.imag*num.imag;
        this.imag = num.real*this.imag + temp*num.imag;
    }

    public void square(){
        double temp = real;
        this.real = temp * temp - imag * imag;
        this.imag = temp * imag + temp * imag;
    }

    public double sumSquares(){
        return  (this.real * this.real + this.imag * this.imag);
    }

}
