package com.example.focus_now.ui.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.focus_now.R
import com.example.focus_now.ui.fragment.CreateTaskDirections
import com.example.focus_now.ui.fragment.MyDay
import com.example.focus_now.ui.fragment.MyDayDirections
import com.example.focus_now.ui.viewmodel.StateAppViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val controlador by lazy {
        findNavController(R.id.activity_main_nav_host)
    }

    private val viewModel: StateAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configurandoNavigationDrawer()
        supportActionBar?.hide()


        controlador.addOnDestinationChangedListener { controller, destination, arguments ->

            viewModel.componentLiveData.observe(this@MainActivity, Observer { visualComponent ->
                title = if(visualComponent.title){
                    destination.label
                }else{
                    ""
                }
                if (visualComponent.appBar) {
                    supportActionBar?.show()
                }

                val fab = findViewById<FloatingActionButton>(R.id.float_cadastrar_tarefas)
                if(visualComponent.floatActionButton) {
                    fab.show()
                    fab.setOnClickListener { irParaTelaCadastroTarefas() }
                }else{
                    fab.hide()
                }
            })

        }
    }

    private fun irParaTelaCadastroTarefas() {
        val direcao = MyDayDirections.actionMeuDiaToCriarTarefas()
        controlador.navigate(direcao)
    }

    private fun configurandoNavigationDrawer() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.drawerElevation = 3f

        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawer,
            toolbar,
            R.string.opendrawer,
            R.string.closedrawer
        )
        toggle.drawerArrowDrawable.color = Color.WHITE
        toggle.isDrawerIndicatorEnabled = true
        toggle.toolbarNavigationClickListener =
            View.OnClickListener { drawer.openDrawer(GravityCompat.START) }

        toggle.syncState()


        val navigation = findViewById<NavigationView>(R.id.nav_view)
        navigation.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.my_day -> {
                    val direcao = MyDayDirections.actionGlobalMeuDia()
                    controlador.navigate(direcao)
                }

                R.id.task -> {
                    val direcao = MyDayDirections.actionGlobalListaTarefas()
                    controlador.navigate(direcao)
                }

                R.id.print -> {
                    val direcao = MyDayDirections.actionGlobalRelatorios()
                    controlador.navigate(direcao)
                }

                R.id.settings -> {
                    val direcao = MyDayDirections.actionGlobalConfiguracao()
                    controlador.navigate(direcao)
                }

                R.id.app_lock -> {
                    val direcao = MyDayDirections.actionGlobalAppLock()
                    controlador.navigate(direcao)
                }
                R.id.donate -> {
                    val direcao = MyDayDirections.actionGlobalCafe()
                    controlador.navigate(direcao)
                }
                R.id.sobre -> {
                    val direcao = MyDayDirections.actionGlobalSobre()
                    controlador.navigate(direcao)
                }
            }


            drawer.close()
            true
        }

    }

}