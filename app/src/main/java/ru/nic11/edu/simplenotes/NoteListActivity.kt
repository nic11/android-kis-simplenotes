package ru.nic11.edu.simplenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import de.cketti.mailto.EmailIntentBuilder

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        title = "Notes"

        val card: CardView = findViewById(R.id.card_view)
        card.setOnClickListener {
            Toast.makeText(applicationContext, "kek", LENGTH_SHORT).show()
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        val emailBtn: AppCompatButton = findViewById(R.id.send_email_button)
        emailBtn.setOnClickListener {
            Toast.makeText(applicationContext, "email", LENGTH_SHORT).show()
            val succ = EmailIntentBuilder.from(applicationContext)
                .to("support@example.com")
                .subject("Notes feedback")
                .start()
        }
    }
}
