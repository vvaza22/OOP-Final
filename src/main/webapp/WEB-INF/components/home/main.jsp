<main class="main-content pt-4">
    <div class="container">
        <div class="row">
            <div class="col-4">
                <!-- Popular Quizzes -->
                <jsp:include page="g_popular.jsp" />

                <!-- Recent Quizzes -->
                <jsp:include page="g_recent.jsp" />
            </div>
            <div class="col-8">
                <!-- Admin announcements -->
                <jsp:include page="g_anno.jsp" />
            </div>
        </div>
    </div>
</main>