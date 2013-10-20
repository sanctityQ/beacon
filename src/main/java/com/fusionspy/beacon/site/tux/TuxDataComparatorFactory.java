package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.tux.entity.TuxquesEntity;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.fusionspy.beacon.site.tux.entity.DataSave;

import java.util.Comparator;

/**
 * tux data
 * User: qc
 * Date: 11-10-11
 * Time: 上午10:23
 */
public class TuxDataComparatorFactory {

       static Comparator getComparatorQue(){
          return new Comparator<TuxquesEntity>() {
                @Override
                public int compare(TuxquesEntity o1, TuxquesEntity o2) {
                    return o2.getQueued() - o1.getQueued();
                }
            } ;
       }

       static Comparator getComparatorServer(DataSave.Server type) {
            if (type.equals(DataSave.Server.CPU)) {
                return new Comparator<TuxsvrsEntity>() {
                    @Override
                    public int compare(TuxsvrsEntity o1, TuxsvrsEntity o2) {
                        return Float.floatToIntBits(o2.getCpuuse() - o1.getCpuuse());
                    }
                };
            } else if (type.equals(DataSave.Server.MEMORY)) {
                return new Comparator<TuxsvrsEntity>() {
                    @Override
                    public int compare(TuxsvrsEntity o1, TuxsvrsEntity o2) {
                        return o2.getMemoryuse() - o1.getMemoryuse();
                    }
                };
            }
            //defult rqdone
            else {
                return new Comparator<TuxsvrsEntity>() {
                    @Override
                    public int compare(TuxsvrsEntity o1, TuxsvrsEntity o2) {
                        return o2.getRqdone() - o1.getRqdone();
                    }
                };
            }
        }
}
