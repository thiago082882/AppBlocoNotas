package com.thiago.appblocodenotas

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thiago.appblocodenotas.datastore.StoreAnotacao
import com.thiago.appblocodenotas.ui.theme.AppBlocoDeNotasTheme
import com.thiago.appblocodenotas.ui.theme.BLACK
import com.thiago.appblocodenotas.ui.theme.GOLD
import com.thiago.appblocodenotas.ui.theme.WHITE
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBlocoDeNotasTheme {
                BlocoNotasComponents()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocoNotasComponents() {

    val context = LocalContext.current

    val storeAnotacao = StoreAnotacao(context)

    val scope = rememberCoroutineScope()
    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(
        initial = ""
    )

    var anotacao by remember {
        mutableStateOf("")
    }

    anotacao = anotacaoSalva.value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = GOLD),
                title = {
                    Text(
                        text = "Bloco de Notas",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = BLACK
                    )
                }
            )

        },
        // floatingActionButton: @Composable () -> Unit = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao)
                        Toast.makeText(context, "Anotação feita com sucesso", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                containerColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),

                ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.save),
                    contentDescription = "Icone de Salvar Anotação"

                )
            }
        }
    ) {
        Column() {
            TextField(
                value = anotacao,
                onValueChange = {
                    anotacao = it
                },
                label = {
                    Text(text = "Digite a sua anotação...")
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BlocoNotasComponentsPreview() {
    AppBlocoDeNotasTheme {
        BlocoNotasComponents()
    }
}