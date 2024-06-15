<main class="ac-access-page">
    <div class="form-container">
        <form id="register-form">
            <div class="form-title">
                <h3>Create Account.</h3>
            </div>
            <div id="register_error" style="display: none" class="alert alert-danger login-alert">
                <span id="register_error_msg" class="error-msg"></span>
                <a href="#" id="error_close"> <i class="fa-solid fa-x"></i> </a>
            </div>
            <div class="row input-cont">
                <div class="col">
                    <input id="firstname" type="text" class="form-control" name="first_name" placeholder="First Name" />
                </div>
                <div class="col">
                    <input id="lastname" type="text" class="form-control" name="last_name" placeholder="Last Name" />
                </div>
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
                    <input type="submit" class="btn btn-primary" name="submit" value="Sign Up" />
                </div>
            </div>
            <span>Already registered? <a href="/login">Sign In</a>.</span>
        </form>
    </div>
</main>