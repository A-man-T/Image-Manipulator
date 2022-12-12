package assignment;

/*
 *
 * CS314H Programming Assignment 1 - Java image processing
 *
 * Included is the Invert effect from the assignment.  Use this as an
 * example when writing the rest of your transformations.  For
 * convenience, you should place all of your transformations in this file.
 *
 * You can compile everything that is needed with
 * javac -d bin src/main/java/assignment/*.java
 *
 * You can run the program with
 * java -cp bin assignment.JIP
 *
 * Please note that the above commands assume that you are in the prog1
 * directory.
 */

import java.util.ArrayList;

//These classes iterate through the pixel array and remove the red/green/blue component from each entry.
class NoRed extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int width = pixels[0].length;
        int height = pixels.length;
        if(pixels == null)
            return null;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x]=makePixel(0,getGreen(pixels[y][x]),getBlue(pixels[y][x]));
            }
        }
        return pixels;
    }
}
class NoGreen extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int width = pixels[0].length;
        int height = pixels.length;
        if(pixels == null)
            return null;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x]=makePixel(getRed(pixels[y][x]),0,getBlue(pixels[y][x]));
            }
        }
        return pixels;
    }
}

class NoBlue extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x]=makePixel(getRed(pixels[y][x]),getGreen(pixels[y][x]),0);
            }
        }
        return pixels;
    }
}

//These classes iterate through the pixel array and remove all the non-red/non-green/non-blue components from each entry.
class RedOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                pixels[y][x]=makePixel(getRed(pixels[y][x]),0,0);

        }
        return pixels;
    }
}

class GreenOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                pixels[y][x]=makePixel(0,getGreen(pixels[y][x]),0);

        }
        return pixels;
    }
}

class BlueOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
                pixels[y][x]=makePixel(0,0,getBlue(pixels[y][x]));

        }
        return pixels;
    }
}
//This class turns a color image into a black and white image. It iterates through the array
// and for each pixel averages the red, blue, and green components.
// Then it sets each component of the chosen pixel to that average.
class BlackAndWhite extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;
        int avg;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                avg = (getBlue(pixels[y][x]) + getGreen(pixels[y][x]) + getRed(pixels[y][x]))/3;
                pixels[y][x] = makePixel(avg, avg, avg);
            }
        }
        return pixels;
    }
}
//This class reflects the image around a horizontal line across the middle of the original image. It iterates
// through the top half of the array, then using a temp variable swaps values with the
// corresponding pixel on the bottom side of the array.
class HorizontalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;
        int temp;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height/2; y++) {
                temp = pixels[y][x];
                pixels[y][x]= pixels[height-1-y][x];
                pixels[height-1-y][x]= temp;

            }
        }
        return pixels;
    }
}
//This class reflects the image around a vertical line down the middle of the original image. It iterates
// through the left half of the array,
// then using a temp variable swaps values with the corresponding pixel on the right side of the array.
class VerticalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;
        int temp;
        for (int x = 0; x < width/2; x++) {
            for (int y = 0; y < height; y++) {
                temp = pixels[y][x];
                pixels[y][x]= pixels[y][width-1-x];
                pixels[y][width-1-x] = temp;

            }
        }
        return pixels;
    }
}
//This class creates an image that is twice as high and twice as wide as the original image.
// It maps each pixel in the original image to a 2x2 box of pixels in the new image.
class Grow extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] newimage = new int[2*height][2*width];


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newimage[2*y][2*x]=newimage[2*y+1][2*x]=newimage[2*y][2*x+1]=newimage[2*y+1][2*x+1]=pixels[y][x];

            }
        }
        return newimage;
    }
}
//This class creates an image that is half as high and half as wide as your original picture.
// Each pixel in the new image is a pixel that is the average of the RGB components of a 2x2 box of pixels
// in the original image.
class Shrink extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        int width = pixels[0].length;
        int height = pixels.length;
        int avgRed, avgGreen, avgBlue;
        if((width == 1) || (height == 1))
            return pixels;

        int[][] newimage = new int[height/2][width/2];


        for (int x = 0; x < width/2; x++) {
            for (int y = 0; y < height/2; y++) {

                avgRed = (getRed(pixels[2*y+1][2*x])+getRed(pixels[2*y][2*x+1])+getRed(pixels[2*y+1][2*x+1])+getRed(pixels[2*y][2*x]))/4;
                avgGreen =(getGreen(pixels[2*y+1][2*x])+getGreen(pixels[2*y][2*x+1])+getGreen(pixels[2*y+1][2*x+1])+getGreen(pixels[2*y][2*x]))/4;
                avgBlue = (getBlue(pixels[2*y+1][2*x])+getBlue(pixels[2*y][2*x+1])+getBlue(pixels[2*y+1][2*x+1])+getBlue(pixels[2*y][2*x]))/4;
                newimage[y][x]= makePixel(avgRed,avgGreen,avgBlue);


            }
        }
        return newimage;
    }

}
//This class reduces the number of colors used in the image to eight.
// Based on a passed parameter threshold value, each of the R, G, and B values of a pixel in the output image
// are either fully on (255) or fully off (0). If a component is above a threshold, it is set to 255,
// and if less than or equal to the threshold, the component is set to 0.
class Threshold extends ImageEffect {
    {
        params = new ArrayList<>();
        params.add(new ImageEffectParam("Threshold",
                "Pick a threshold value",
                127, 0, 255));
    }

    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        if(pixels == null)
            return null;
        if(!((params.get(0).getValue()>=0)&&(params.get(0).getValue()<=255)))
            return null;


        int width = pixels[0].length;
        int height = pixels.length;
        int newRed;
        int newGreen;
        int newBlue;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (getBlue(pixels[y][x]) > params.get(0).getValue())
                    newBlue = 255;
                else
                    newBlue = 0;
                if (getGreen(pixels[y][x]) > params.get(0).getValue())
                    newGreen = 255;
                else
                    newGreen = 0;
                if (getRed(pixels[y][x]) > params.get(0).getValue())
                    newRed = 255;
                else
                    newRed = 0;

                pixels[y][x] = makePixel(newRed, newGreen, newBlue);
            }


        }
        return pixels;
    }
}

class Invert extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = ~pixels[y][x];
            }
        }
        return pixels;
    }
}

class Dummy extends ImageEffect {

    public Dummy() {
        params = new ArrayList<>();
        params.add(new ImageEffectParam("Threshold",
                "Pick a threshold value",
                10, 0, 1000));
    }

    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        // Use params here.
        return pixels;
    }
}
