<main class="ac-access-page">
    <div class="form-container">
        <form id="login-form" action="/login" method="post">
            <div class="form-title">
                <h3>Log into your account.</h3>
            </div>
            <div class="row input-cont">
                <div class="col">
                    <input id="username" type="text" class="form-control" name="username" placeholder="Username" />
                </div>
            </div>
            <div class="row input-cont">
                <div class="col">
                    <input id="password" type="password" class="form-control" name="password" placeholder="Password" />
                </div>
            </div>
            <div class="row input-cont submit-cont">
                <div class="col">
                    <input type="submit" class="btn btn-primary" name="submit" value="Sign In" />
                </div>
            </div>
            <span>Not registered yet? <a href="/register">Sign Up.</a></span>
        </form>
    </div>
</main>

<script src="/js/login.js" type="text/javascript"></script>