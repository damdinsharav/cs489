package com.example.btv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.btv.models.Channel
import com.example.btv.models.Program
import com.example.btv.ui.theme.BtvTheme
import com.example.btv.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

class MainActivity : ComponentActivity() {
    private var channels = mutableStateListOf<Channel>()
    private var programs = mutableStateListOf<Program>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtvTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    getChannels()
                    MyUi(
                        channels = channels,
                        programs = programs,
                        onDateRangeClick = { channelNumber ->
                            getPrograms(channelNumber)
                        }
                    )
                }
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun getChannels() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getChannels()
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    channels.clear()
                    channels.addAll(response.body()!!)

                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getPrograms(channelNumber: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getProgramsByChannel(channelNumber)
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    programs.clear()
                    programs.addAll(response.body()!!)

                }
            }
        }
    }
}

@Composable
fun MyUi(
    channels: SnapshotStateList<Channel>,
    programs: SnapshotStateList<Program>,
    onDateRangeClick: (Int) -> Unit
) {
    val context = LocalContext.current
    var baseUrl = "http://la.btv.mn:83/mnb/index.m3u8"
    Row {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            channels.forEach { channel ->
                val text = "${channel.number}. ${channel.name}"
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(
                        text = text, modifier = Modifier
                            .padding(start = 50.dp, top = 10.dp, end = 50.dp, bottom = 10.dp)
                            .width(200.dp)
                    )

                    Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = stringResource(id = R.string.play_button_desc),
                        modifier = Modifier
                            .width(50.dp)
                            .clickable {
                                val intent = Intent(context, VideoPlayerActivity::class.java)
                                intent.putExtra("Key", channel!!.urlPath)
                                context.startActivity(intent)
                            }
                    )

                    Icon(
                        Icons.Rounded.DateRange,
                        contentDescription = stringResource(id = R.string.play_button_desc),
                        modifier = Modifier
                            .width(50.dp)
                            .clickable {
                                baseUrl = channel.urlPath
                                onDateRangeClick(channel.number)
                            }
                    )
                }


            }
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            programs.forEach { program ->
                val text = "${program.start.replace('T', ' ').dropLast(3)}: ${program.title}"
                Row(verticalAlignment = CenterVertically) {
                    Text(
                        text = text, modifier = Modifier
                            .padding(start = 150.dp, top = 10.dp, end = 50.dp, bottom = 10.dp)
                            .width(400.dp)
                    )

                    Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = stringResource(id = R.string.play_button_desc),
                        modifier = Modifier
                            .width(50.dp)
                            .clickable {
                                val intent = Intent(context, VideoPlayerActivity::class.java)
                                val unix = LocalDateTime
                                    .parse(program.start)
                                    .toInstant(
                                        ZoneOffset.UTC
                                    ).epochSecond
                                val fulluri = baseUrl.replace(
                                    "index",
                                    "index-${unix}-${program.duration}"
                                )

                                println(fulluri)
                                intent.putExtra("Key", fulluri)
                                context.startActivity(intent)
                            }
                    )

                }


            }
        }
    }


}
