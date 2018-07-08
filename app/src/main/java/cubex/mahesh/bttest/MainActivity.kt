package cubex.mahesh.bttest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bAdapter = BluetoothAdapter.getDefaultAdapter()
        var v:Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
       s1.isChecked = bAdapter.isEnabled
        s1.setOnCheckedChangeListener(
                { compoundButton: CompoundButton, b: Boolean ->
                    if (b) {
                        bAdapter.enable()
                        v.vibrate(5000);
                    } else {
                        bAdapter.disable()
                        v.vibrate(5000);
                    }
                    })
        var list = mutableListOf<String>()
        var adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                list)
        lview.adapter = adapter
        gbt.setOnClickListener({
            bAdapter.startDiscovery()
            var iFilter = IntentFilter( )
            iFilter.addAction(BluetoothDevice.ACTION_FOUND)
            registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
var device:BluetoothDevice =
                    p1!!.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            list.add(device.name + "     "+device.address)
                    adapter.notifyDataSetChanged()
                }
            },iFilter)
        })
    }
}
