package com.example.businesscard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val links = listOf(
                LinkInfo("LinkedIn", "https://www.linkedin.com/in/JoakimEckerman", R.drawable.linkedin_logo),
                LinkInfo("Call", "tel:6615194818", R.drawable.phone_logo),
                LinkInfo("GitHub", "https://github.com/JoakimEckerman?tab=repositories", R.drawable.github_logo),
                LinkInfo("Email", "mailto:Joakim.eckerman98@gmail.com", R.drawable.email_logo)
            )
            BusinessCard(
                name = stringResource(R.string.signature),
                title = stringResource(R.string.title),
                links = links,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun BusinessCard(
    name: String,
    title: String,
    links: List<LinkInfo>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFB5B5B5)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            // Top 2/3 of the screen
            Column(
                modifier = Modifier.fillMaxWidth().weight(2f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val image = painterResource(R.drawable.profile_picture)
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(150.dp).clip(RoundedCornerShape(8.dp))
                    )
                    Text(
                        text = name,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = title,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between top and bottom sections

            // Bottom 1/3 of the screen, split into two columns
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                // Left column for the first two links
                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly, // Evenly space the rows vertically
                    horizontalAlignment = Alignment.CenterHorizontally // Center links horizontally
                ) {
                    ClickableTextWithLink(links[0])
                    ClickableTextWithLink(links[1])
                }

                // Right column for the second two links
                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly, // Evenly space the rows vertically
                    horizontalAlignment = Alignment.CenterHorizontally // Center links horizontally
                ) {
                    ClickableTextWithLink(links[2])
                    ClickableTextWithLink(links[3])
                }
            }
        }
    }
}


data class LinkInfo(val linkText: String, val url: String, val iconResource: Int)

@Composable
fun ClickableTextWithLink(linkInfo: LinkInfo) {
    val context = LocalContext.current

    // Wrap the entire Row in a clickable modifier
    Row(
        modifier = Modifier.clickable {
            when {
                linkInfo.url.startsWith("tel:") -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(linkInfo.url))
                    context.startActivity(intent)
                }
                linkInfo.url.startsWith("mailto:") -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse(linkInfo.url)
                    context.startActivity(intent)
                }
                else -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkInfo.url))
                    context.startActivity(intent)
                }
            }
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(linkInfo.iconResource),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(linkInfo.linkText)
                    addStringAnnotation(
                        tag = "URL",
                        annotation = linkInfo.url,
                        start = 0,
                        end = linkInfo.linkText.length
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardTheme {
        val links = listOf(
            LinkInfo("LinkedIn", stringResource(R.string.linkedIn), R.drawable.linkedin_logo),
            LinkInfo("Call", stringResource(R.string.phone), R.drawable.phone_logo),
            LinkInfo("GitHub", stringResource(R.string.gitHub), R.drawable.github_logo),
            LinkInfo("Email", stringResource(R.string.email), R.drawable.email_logo)
        )
        BusinessCard(
            name = stringResource(R.string.signature),
            title = stringResource(R.string.title),
            links = links,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}