package mx.edu.potros.practica9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.time.TimeMark

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave: Button = findViewById(R.id.save_button)
        btnSave.setOnClickListener { saveUserFromForm() }

        userRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error en caso de cancelación de la operación
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousName: String?) {
                // Manejar el movimiento de un hijo en la base de datos
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousName: String?) {
                // Manejar el cambio en un hijo de la base de datos
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // Manejar la eliminación de un hijo de la base de datos
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val usuario = dataSnapshot.getValue(User::class.java)
               if(usuario!=null) writeMark(usuario)
            }
        })
    }

    private fun saveUserFromForm() {
        val name: EditText = findViewById(R.id.et_name)
        val lastName: EditText = findViewById(R.id.et_lastname)
        val age: EditText = findViewById(R.id.et_age)

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )
        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User) {
        val listV: TextView = findViewById(R.id.list_textview) as TextView
        val text = listV.text.toString()+ mark.toString()+ "/n"
        listV.text = text
    }
}
