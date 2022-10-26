package mx.com.satoritech.satoritickets.ui.ticket

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.utils.DialogLoading
import mx.com.satoritech.satoritickets.utils.ToolBar
import mx.com.satoritech.satoritickets.ui.auth.LoginViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.lifecycle.Observer
import mx.com.satoritech.satoritickets.ui.ui.*
import okhttp3.RequestBody.Companion.toRequestBody

private var pdfString = ""
private var filePDF =  File("")

@Composable
fun AddTicketScreen(
    navController: NavController,
    viewModel: TicketViewModel = hiltViewModel(),
    userViewModel: LoginViewModel = hiltViewModel(),
){
    val user by userViewModel.user.observeAsState()
    val context = LocalContext.current
    val loading by remember{
        viewModel.loading
    }

    if(loading){
        DialogLoading()
    }

    ToolBar(
        navController = navController,
        showLogout = false,
        title = stringResource(id = R.string.addTicket),
    ) {
        LaunchedEffect(viewModel){
            viewModel.getUsers(context)
        }
        TicketForm(navController, viewModel, user)
    }
}

@Composable
fun TicketForm(
    navController: NavController,
    viewModel: TicketViewModel,
    user: User?
){
    viewModel.setCreatorId("${user?.id?:0}")

    val devices= listOf("Web", "iOS", "Android")

    var errorDevice = ""
    var errorContent = ""
    var errorProject = ""

    val text = stringResource(id = R.string.selectDevice)
    var select by remember {
        mutableStateOf(text)
    }
    var namePdf by remember { mutableStateOf("") }
    val context = LocalContext.current

    val project by remember { viewModel.project }
    val projectError by remember { viewModel.projectError }
    val projectErrorBoolean by remember { viewModel.projectErrorBoolean }

    val content by remember { viewModel.content }
    val contentError by remember { viewModel.contentError }
    val contentErrorBoolean by remember { viewModel.contentErrorBoolean }

    val deviceErrorBoolean by remember { viewModel.deviceErrorBoolean }

    var success by remember {
        viewModel.showDialog
    }
    val showDialog = remember { mutableStateOf(false) }
    if(success) showDialog.value = true

    if(success) {
        AddTicketDialog(
            onDemissR = {
                success = false
                navController.popBackStack()
            }
        )
    }

    /* -------------------------------------- Permisos de acceso al almacenamiento externo -------------------------------------- */
    var externalStoragePermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if(isGranted){
                externalStoragePermission = isGranted
            } else {
                Toast.makeText(context, "El permiso es necesario para subir PDF", Toast.LENGTH_SHORT).show()
            }
        })
    /* -------------------------------------- Permisos de acceso al almacenamiento externo -------------------------------------- */

    /* ---------------------------------------------- SELECCIONAR PDF ----------------------------------------------------- */
    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    val storageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        pdfUri = it.data?.data
        pdfString = pdfUri.toString()
        filePDF = File(pdfUri?.path ?: "")
    }
    namePdf = filePDF.name
    /* ---------------------------------------------- SELECCIONAR PDF ----------------------------------------------------- */

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(shakespeare)
        .height(20.dp))
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color.White)
            //.padding(top = 20.dp)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.noTicket),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = Avenir,
                modifier = Modifier.weight(.5f)
            )
            Text(
                text = stringResource(id = R.string.date),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = Avenir,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(.7f)
                    .padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        RowFields(viewModel)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.project),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = Avenir,
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = project,
            onValueChange = {
                viewModel.setProject(it)
                viewModel.setErrorProject(false)
                            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = shakespeare,
                focusedBorderColor = shakespeare,
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.projectName),
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    fontFamily = Avenir,
                    color = Color.Gray
                )},
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = Avenir,
                color = Color.Black),
            singleLine = true,
            isError = projectErrorBoolean
        )
        if(projectErrorBoolean)
            errorProject = if(projectError != 0) stringResource(id = projectError) else ""

        Text(
            text = errorProject,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.device),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = Avenir,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            var expanded by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(
                            0.5.dp,
                            SolidColor(if (!deviceErrorBoolean) shakespeare else Color.Red)
                        )
                    )
                    .clickable {
                        expanded = !expanded
                        viewModel.setErrorDevice(false)
                    },
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp)
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = select,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        fontFamily = Avenir,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_keyboard_arrow_down),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(pictonBlue)
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .clickable { expanded = true }
            ) {
                devices.forEach { device ->
                    DropdownMenuItem(onClick = {
                        select = device
                        expanded = false
                        viewModel.setDevice(device)
                    }) {
                        Text(
                            device,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            fontFamily = Avenir,
                        )
                    }
                }
            }
        }
        errorDevice = if(deviceErrorBoolean) stringResource(id = R.string.errorSelectDevice) else ""

        Text(
            text = errorDevice,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.userId),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = Avenir,
        )
        Spacer(modifier = Modifier.height(10.dp))

        UsersList(viewModel)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.content),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = Avenir,
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            value = content,
            onValueChange = {
                viewModel.setContent(it)
                viewModel.setErrorContent(false)
                            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = shakespeare,
                focusedBorderColor = shakespeare,
            ),
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = Avenir,
                color = Color.Black),
            isError = contentErrorBoolean
        )
        if(contentErrorBoolean)
            errorContent = if(contentError != 0) stringResource(id = contentError) else ""

        Text(
            text = errorContent,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.pdf),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = Avenir,
        )
        Text(
            text = namePdf,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = Avenir,
        )
        Button(
            onClick = {
                if(externalStoragePermission){
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        .apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "application/pdf"
                        }
                    storageLauncher.launch(intent)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } },
            colors = ButtonDefaults.buttonColors(Color.Red),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string._add),
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    color = Color.White
                )
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_pdf),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White))
            }
        }
        viewModel.setPdf(pdfString)

        Spacer(modifier = Modifier.height(30.dp))
        Card(
            elevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .padding(bottom = 35.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = wattle),
                onClick = {
                    if((pdfUri?.path ?: "") != "") {
                        val mimeType = pdfUri.let { context.contentResolver.getType(it!!) }
                        context.contentResolver.openInputStream(pdfUri!!).use { inputStream ->
                            val document = MultipartBody.Part.createFormData(
                                "pdf",
                                filePDF.name,
                                inputStream!!.readBytes()
                                    .toRequestBody(mimeType?.toMediaTypeOrNull())
                            )
                            viewModel.documentPDF.value = document
                        }
                    } else { Toast.makeText(context, "Debes agregar un PDF", Toast.LENGTH_LONG).show() }

                    viewModel.addTicket(context) { namePdf = "" }
                }
            ) {
                Text(
                    color = Color.Black,
                    text = stringResource(id = R.string.add),
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                )
            }
        }
    }
}

@Composable
fun RowFields(viewModel: TicketViewModel){

    var errorNoTicket = ""

    val noTicket by remember { viewModel.noTicket }
    val noTicketError by remember { viewModel.noTicketError }
    val noTicketErrorBoolean by remember { viewModel.noTicketErrorBoolean }

    val date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now())

    Column(Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                value = noTicket,
                onValueChange = {
                    viewModel.setNoTicket(it)
                    viewModel.setErrorNoTicket(false)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = shakespeare,
                    focusedBorderColor = shakespeare,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.noTicket),
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        fontFamily = Avenir,
                        color = Color.Gray
                    )
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    color = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = noTicketErrorBoolean
            )

            Spacer(modifier = Modifier.width(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.CenterVertically)
                    .border(
                        BorderStroke(0.5.dp, SolidColor(shakespeare)),
                        RoundedCornerShape(4.dp)
                    )
                    .weight(0.7f),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = date,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                viewModel.setDate(date)
            }
        }
        if (noTicketErrorBoolean)
            errorNoTicket = if (noTicketError != 0) stringResource(id = noTicketError) else ""

        Text(
            text = errorNoTicket,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
    }
}

@Composable
fun UsersList(viewModel: TicketViewModel) {
    var errorUser = ""
    val userErrorBoolean by remember { viewModel.userIdErrorBoolean }

    var listUsers = listOf<User>()
    val owner = LocalLifecycleOwner.current
    viewModel.listUser.observe(owner, Observer { list ->
        listUsers = list
    })
    val text = "Selecciona un usuario"
    var select by remember {
        mutableStateOf(text)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        var expanded by remember { mutableStateOf(false) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        0.5.dp,
                        SolidColor(if (!userErrorBoolean) shakespeare else Color.Red)
                    )
                )
                .clickable {
                    expanded = !expanded
                    viewModel.setErrorUser(false)
                }, shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 5.dp)
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = select,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_keyboard_arrow_down),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(pictonBlue)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
                .clickable { expanded = true }
        ) {
            listUsers.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    select = s.name?:""
                    expanded = false
                    viewModel.setUserId("${s.id?:0}")
                }) {
                    Text(
                        text = s.name?:"",
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        fontFamily = Avenir,
                    )
                }
            }
        }
    }
    errorUser = if(userErrorBoolean) stringResource(id = R.string.errorSelectUser) else ""

    Text(
        text = errorUser,
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color.Red,
        modifier = Modifier.padding(start = 15.dp)
    )
}
