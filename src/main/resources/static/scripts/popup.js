window.addEventListener('load', function () {
    document.getElementById("close-btn").onclick = function(e) { return myHandler(e); };

    [...document.getElementsByClassName("competition-link")].forEach(element => {
        element.onclick = function() { return openEmbedInfo(element.id, false, false); };
    });
    [...document.getElementsByClassName("job-link")].forEach(element => {
        element.onclick = function() { return openEmbedInfo(element.id, true, false); };
    });
    [...document.getElementsByClassName("news-link")].forEach(element => {
        element.onclick = function() { return openEmbedInfo(element.id, false, true); };
    });
    [...document.getElementsByClassName("review-link")].forEach(element => {
        element.onclick = function() { return openEmbedReview(element.id); };
    });

    [...document.getElementsByClassName("review-btn")].forEach(element => {
        element.onclick = function() { return openReview(element.id.split("")[0]); };
    });
});

function myHandler(competition) {
    document.getElementById("page-display").style.visibility = "hidden";
    document.getElementById("page-display2").style.visibility = "hidden";
    competition.prcompetitionDefault();
}

async function openEmbedInfo(id, isJob, isNews) {
    if(!isJob) {
        if(isNews) {
            document.getElementById("frame").src = "/news-info-min/" + id;
            await sleep(100);
            document.getElementById("page-display").style.visibility = "visible";
        } else {
            document.getElementById("frame").src = "/competition-info-min/" + id;
            await sleep(100);
            document.getElementById("page-display").style.visibility = "visible";
        }
    } else {
        document.getElementById("frame").src = "/job-info-min/" + id;
        await sleep(100);
        document.getElementById("page-display").style.visibility = "visible";
    }
}

async function openEmbedReview(id) {
    document.getElementById("frame").src = "/competition-review-min/" + id;
    await sleep(100);
    document.getElementById("page-display").style.visibility = "visible";
}

async function openReview(id) {
    document.getElementById("frame-review").src = "/review/" + id;
    await sleep(100);
    document.getElementById("page-display2").style.visibility = "visible";
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

window.onmessage = async function(e) {
    if (e.data == 'done') {
        document.getElementById("page-display2").style.visibility = "hidden";
    }
};