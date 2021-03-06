/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mod.jd.botapi.Bot.Body;

/**
 * This serves as a Base class which implements interface Body and sets up basic working, common for all Bodies.
 * It also registers all classes with the Bot class as a body.
 * @see Body for more documentation.
 * @see mod.jd.botapi.Bot.BasicActions for more documentation.
 */
public abstract class EmptyBody implements Body {
    // Stores if the entity is binded.
    protected boolean isBinded;

    @Override
    public void unbindEntity()
    {
        stopMoving();
        stopInteractItemInHand();
        stopBreakingBlock();
        getSensor().unbindSensor();
        isBinded = false;
    }

    @Override
    public void finalize()
    {
        unbindEntity();
    }

    @Override
    public void faceTowards(double x, double y, double z)
    {
        double yaw,pitch;

        x -= getSensor().getPosition().xCoord;
        x=-x;
        y = y - (getSensor().getPosition().yCoord + getSensor().getEntity().getEyeHeight());
        z -= getSensor().getPosition().zCoord;
        yaw=getYawFromOriginToPoint(x,z);

        if(y==0)
        {
            pitch=0;
        }
        else
        {
            pitch = getPitchFromOriginToPoint(Math.sqrt((x*x)+(z*z)),y);
        }

        turnToPitch(pitch);
        turnToYaw(yaw);
    }
    private static double getYawFromOriginToPoint(double x, double y)
    {
        if(x==0&&y==0)return 0;
        return 90 - Math.toDegrees(Math.atan2(y,x));
    }

    private static double getPitchFromOriginToPoint(double x, double y)
    {
        if(y==0)return 0;
        double ans= Math.toDegrees(Math.atan2(y,x));
        if(ans>90||ans<-90)
        {
            if(y<0)
            {
                ans = 180 + ans;
                ans=-ans;
            }
            else
            {
                ans = 180 - ans;
            }
        }
        return -ans;
    }

}
