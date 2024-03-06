package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirdave.campusnavigator.R

@Composable
fun DestinationDetail(modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        LazyRow(modifier = modifier){
            items(5){
                PlaceCard()
            }
        }

        Text(
            text = stringResource(id = R.string.placeholder_place_name),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 3
        )

        Row(modifier = modifier.padding(top = 8.dp)){
            ConstraintLayout {
                val (locationIcon, locationText) = createRefs()

                Icon(
                    painter = painterResource(id = R.drawable.baseline_location),
                    contentDescription = null,
                    modifier = modifier.constrainAs(locationIcon){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = "3.5km",
                    modifier = modifier.constrainAs(locationText){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(locationIcon.end)
                    },
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = modifier.width(16.dp))

            ConstraintLayout {
                val (directionIcon, directionText) = createRefs()

                Icon(
                    painter = painterResource(id = R.drawable.baseline_location_city),
                    contentDescription = null,
                    modifier = modifier.constrainAs(directionIcon){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = "Lecture Theatre",
                    modifier = modifier.constrainAs(directionText){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(directionIcon.end)
                    },
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = modifier.height(16.dp))
        
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu),
                        contentDescription = null,
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.how_to_get_there),
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = modifier.width(8.dp))

            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Row{
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_near_me),
                        contentDescription = null,
                    )

                    Spacer(modifier = modifier.width(8.dp))

                    Text(
                        text = stringResource(id = R.string.direct_me),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
    
}

@Composable
fun PlaceCard(modifier: Modifier = Modifier){
    OutlinedCard(
        modifier = modifier.padding(8.dp).height(IntrinsicSize.Min).width(IntrinsicSize.Min),
    ) {
        Box(modifier = modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.ab3_stretching),
                contentDescription = null,
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_zoom_out_map),
                contentDescription = null,
                modifier = modifier.align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 8.dp),
                tint = Color.White
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    DestinationDetail()
}