package com.patriciafiona.tentangku.ui.main.finance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityFinanceBinding
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity

class FinanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddTransaction.setOnClickListener{
                val intent = Intent(this@FinanceActivity, FinanceAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }
}