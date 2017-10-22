public class ComplexNumber {
    public double real;
    public double imag;

    public ComplexNumber(){
        this.real = 0;
        this.imag = 0;
    }

    public ComplexNumber(double real, double imag){
        this.real = real;
        this.imag = imag;
    }

    public void add(ComplexNumber num){
        this.real += num.real;
        this.imag += num.imag;
    }

    public void mult(ComplexNumber num){
        this.real = this.real*num.real - this.imag*num.imag;
        this.imag = num.real*this.imag + this.real*num.imag;
    }

    public double sumSquares(){
        return  Math.pow(this.real, 2) + Math.pow(this.imag, 2);
    }

    public double magnitude(){
        return Math.sqrt( this.sumSquares() );
    }

}
