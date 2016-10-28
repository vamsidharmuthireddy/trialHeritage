package in.ac.iiit.cvit.heritage;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by HOME on 28-10-2016. by vamsi
 */

public class gettingViewAngle extends Activity implements SensorEventListener {


    Float azimuth;  // View to draw a compass
    Float pitch;
    Float roll;

    float oldAzimuth;

    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    float[] mGravity;
    float[] mGeomagnetic;

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {

                // orientation contains azimut, pitch and roll
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                oldAzimuth = azimuth;
                // at this point, orientation contains the azimuth(direction), pitch and roll values.
//                azimuth = (float) (180 * orientation[0] / Math.PI);
//                pitch = (float)(180 * orientation[1] / Math.PI);
//                roll = (float) (180 * orientation[2] / Math.PI);

            }
        }
    }

    /**
     * This method calculates the azimuthal angle and line equation of the mobile's axis
     * @param currentLatitude
     * @param currentLongitude
     * @return co-efficients of the line a.x + b.y + c = 0
     */
    public double[] setLine(Double currentLatitude, Double currentLongitude){

        double angle = 1;
        double a,b,c;
        double[] coEfficients = {1, 1, 0};

        if(azimuth!= null) {
            angle = (float) azimuth;
            if (angle == 0){
                angle = Math.PI/180;
            }
            if ( angle%((Math.PI)/2) ==0){
                a = 0;
                b = 1;
                c = ( - currentLongitude);
            }
            else {
                a = -(Math.tan((double) angle));
                b = 1;
                c = (Math.tan((double) angle) * currentLatitude) - currentLongitude;
            }
            Log.d("setLine:azimuth", "azimuth = "+ angle);

            coEfficients[0] = a ;
            coEfficients[1] = b ;
            coEfficients[2] = c ;

        }
        else{
            angle = (float) oldAzimuth;
            if (angle == 0){
                angle = Math.PI/180;
            }

            if ( angle%((Math.PI)/2) ==0){
                a = 0;
                b = 1;
                c = ( - currentLongitude);
            }
            else {
                a = -(Math.tan((double) angle));
                b = 1;
                c = (Math.tan((double) angle) * currentLatitude) - currentLongitude;
            }
            Log.d("setLine:oldAzimuth", "oldAzimuth = "+ angle);

            coEfficients[0] = a ;
            coEfficients[1] = b ;
            coEfficients[2] = c ;
        }



        return coEfficients;
    }

}
