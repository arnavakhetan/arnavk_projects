-- Final Project by Piyush & Arnav
USE final_project;

/* Core Question
What helps a College basketball team be successful and how does that translate to their players being drafted into the NBA? */

/* Data Cleaning
We did not do any data cleaning inside SQL itself. All of it was done in the csv files themself in Excel (before we imported the data).
We got errors while uploading the data to SQL and that caused SQL to completely omit columns/rows at times so we decided it best to just do all the cleaning in Excel.

In the cbb dataset, we had to edit the POSTSEASON and SEED columns because they of the nature of how the columns work. 
If a team has not ranked high enough to get seeded then they didn't have a seed/nor a value for postseason.
Due to this, we decided to leave postseason as blank because its a text type when the team wasn't seeded (and kept seed as 0 because its int type).

In the nba dataset, the draft_year, draft_round, draft_number columns contained the word "Undrafted" while all 3 columns are int types. 
So we edited all "Undrafted" to 0 so that SQL would be able to import the data.

The draft_data dataset was pulled from Wikipedia and slightly edited in terms of the names of players with accents on letters (like n and o etc)
We had to remove the accents (kept the letters though) as SQL doesn't seem to support those.
*/

/* JOIN
We primarily used inner joins to combine our tables. 
An inner join returns only the rows where there is a match between both tables, which works well for our analysis because we only want players that exist in both datasets. 
For example, when joining the NBA dataset with the draft dataset, we matched players using their names to ensure that we only include players with valid draft and performance data.

Using inner joins helped us avoid including incomplete or unmatched records, which could introduce errors into our analysis. 
Since our focus is on analyzing relationships between college performance, draft position, and NBA performance, it was important that each player included in the results had corresponding data in both tables.

We did not use left or right joins because those would include unmatched rows, such as players without draft information or missing performance data. 
Including those rows would make the analysis less accurate, especially when calculating averages. 
*/

-- Done by Arnav.
-- EDA for cbb (college basketball dataset)

-- Table Structure
DESCRIBE cbb;
-- The table has data about various college basketball teams and their overall statistics including their progress in each conference (tournament) and what final rank they achieved.

-- Total rows count
SELECT COUNT(*) AS total_rows
FROM cbb;

-- NULL vals in main stat columns
SELECT
    COUNT(*) AS total_rows,
    SUM(CASE WHEN TEAM IS NULL THEN 1 ELSE 0 END) AS null_team,
    SUM(CASE WHEN W IS NULL THEN 1 ELSE 0 END) AS null_wins,
    SUM(CASE WHEN WAB IS NULL THEN 1 ELSE 0 END) AS null_wab,
    SUM(CASE WHEN ADJOE IS NULL THEN 1 ELSE 0 END) AS null_adjoe,
    SUM(CASE WHEN ADJDE IS NULL THEN 1 ELSE 0 END) AS null_adjde,
    SUM(CASE WHEN EFG_O IS NULL THEN 1 ELSE 0 END) AS null_efg,
    SUM(CASE WHEN TOR IS NULL THEN 1 ELSE 0 END) AS null_tor
FROM cbb;

-- Basic summary of stats
SELECT
    ROUND(AVG(W), 2) AS avg_wins,
    MIN(W) AS min_wins,
    MAX(W) AS max_wins,

    ROUND(AVG(WAB), 2) AS avg_wab,
    MIN(WAB) AS min_wab,
    MAX(WAB) AS max_wab,

    ROUND(AVG(ADJOE), 2) AS avg_offense,
    ROUND(AVG(ADJDE), 2) AS avg_defense,

    ROUND(AVG(EFG_O), 2) AS avg_shooting,
    ROUND(AVG(TOR), 2) AS avg_turnovers,

    ROUND(AVG(`3P_O`), 2) AS avg_3p
FROM cbb;

-- Higher WAB = Higher AVG Wins
WITH wab_ranked AS (
    SELECT
        YEAR,
        TEAM,
        W,
        WAB,
        NTILE(2) OVER (PARTITION BY YEAR ORDER BY WAB DESC) AS wab_half
    FROM cbb
)
SELECT
    CASE
        WHEN wab_half = 1 THEN 'Top half WAB'
        ELSE 'Bottom half WAB'
    END AS wab_group,
    COUNT(*) AS teams,
    ROUND(AVG(W), 2) AS avg_wins
FROM wab_ranked
GROUP BY wab_half
ORDER BY avg_wins DESC;

-- High O + Strong D, High O + Weak D, Low O + Strong D, Low O + Weak D
SELECT
    team_type,
    COUNT(*) AS teams,
    ROUND(AVG(W), 2) AS avg_wins
FROM (
    SELECT
        W,
        CASE
            WHEN ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
                 AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
                THEN 'High offense + strong defense'
            WHEN ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
                 AND ADJDE > (SELECT AVG(ADJDE) FROM cbb)
                THEN 'High offense + weak defense'
            WHEN ADJOE < (SELECT AVG(ADJOE) FROM cbb)
                 AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
                THEN 'Low offense + strong defense'
            ELSE 'Low offense + weak defense'
        END AS team_type
    FROM cbb
) AS x
GROUP BY team_type
ORDER BY avg_wins DESC;

-- Higher average shooting efficiency = higher average wins
SELECT
    efg_group,
    COUNT(*) AS teams,
    ROUND(AVG(W), 2) AS avg_wins,
    ROUND(AVG(EFG_O), 2) AS avg_efg_o
FROM (
    SELECT
        W,
        EFG_O,
        CASE
            WHEN EFG_O >= (SELECT AVG(EFG_O) FROM cbb) THEN 'Higher shooting efficiency'
            ELSE 'Lower shooting efficiency'
        END AS efg_group
    FROM cbb
) AS x
GROUP BY efg_group;

-- Fewer turnovers = more average wins
SELECT
    turnover_group,
    COUNT(*) AS teams,
    ROUND(AVG(W), 2) AS avg_wins,
    ROUND(AVG(TOR), 2) AS avg_tor
FROM (
    SELECT
        W,
        TOR,
        CASE
            WHEN TOR <= (SELECT AVG(TOR) FROM cbb) THEN 'Fewer turnovers'
            ELSE 'More turnovers'
        END AS turnover_group
    FROM cbb
) AS x
GROUP BY turnover_group;

-- 3-pointer shooting efficiency has similar values across the board and isn't very impactful in terms of avg number of wins
SELECT
    YEAR,
    ROUND(AVG(`3P_O`), 2) AS avg_3p_o,
    MIN(`3P_O`) AS min_3p_o,
    MAX(`3P_O`) AS max_3p_o,
    ROUND(MAX(`3P_O`) - MIN(`3P_O`), 2) AS range_3p_o
FROM cbb
GROUP BY YEAR
ORDER BY YEAR;
-- These queries helped to show which statistics were the most important from this dataset in deciding success for a particular college basketball team.
-- WAB, ADJOE & ADJDE, EFG_O and TOR were found to be the best parameters.
-- While a column like 3P_O had similar values across all teams and didn't contribute much to show that a team was better than another team.

-- Done by Piyush.
-- EDA for nba (NBA Draft Data, with some draft data)

-- Table Structure
DESCRIBE nba;
-- The table has nba player data, how they were drafted and statistics about them during their time in the nba.

-- Total rows count
SELECT COUNT(*) AS total_rows
FROM nba;

-- NULL vals in main stat columns
SELECT
    SUM(CASE WHEN player_name IS NULL THEN 1 ELSE 0 END) AS null_players,
    SUM(CASE WHEN college IS NULL THEN 1 ELSE 0 END) AS null_college,
    SUM(CASE WHEN draft_year IS NULL THEN 1 ELSE 0 END) AS null_draft_year,
    SUM(CASE WHEN draft_number IS NULL THEN 1 ELSE 0 END) AS null_pick,
    SUM(CASE WHEN pts IS NULL THEN 1 ELSE 0 END) AS null_pts
FROM nba;

-- Summary of stats 
SELECT
    ROUND(AVG(pts), 2) AS avg_pts,
    MIN(pts) AS min_pts,
    MAX(pts) AS max_pts,

    ROUND(AVG(reb), 2) AS avg_reb,
    ROUND(AVG(ast), 2) AS avg_ast,

    ROUND(AVG(net_rating), 2) AS avg_net_rating,
    ROUND(AVG(ts_pct), 3) AS avg_ts_pct
FROM nba;

-- Draft pick distribution
SELECT
    CASE
        WHEN draft_number <= 10 THEN 'Top 10 picks'
        WHEN draft_number <= 30 THEN 'First round'
        WHEN draft_number <= 60 THEN 'Second round'
        ELSE 'Undrafted/Other'
    END AS draft_group,
    COUNT(*) AS players
FROM nba
GROUP BY draft_group;

SELECT *
FROM nba
LIMIT 10;

-- Avgs
SELECT 
    AVG(pts) AS avg_points,
    AVG(ast) AS avg_assists,
    AVG(reb) AS avg_rebounds,
    AVG(gp) AS avg_games
FROM nba;

-- Shifts by time 
SELECT season, AVG(pts) AS avg_points
FROM nba
GROUP BY season
ORDER BY season;

-- Top NBA players
SELECT player_name, pts, reb, ast
FROM nba
ORDER BY pts DESC
LIMIT 10;

-- What is a successful NBA player
SELECT 
    AVG(pts) AS avg_pts,
    AVG(gp) AS avg_games
FROM nba
WHERE pts > 15;

-- Comparisons
SELECT 
    n.player_name,
    n.college,
    c.W AS college_wins,
    n.avg_pts,
    n.avg_gp
FROM (
    SELECT 
        player_name,
        college,
        AVG(pts) AS avg_pts,
        AVG(gp) AS avg_gp
    FROM nba
    GROUP BY player_name, college
) n
JOIN cbb c
ON c.TEAM LIKE CONCAT('%', n.college, '%')
AND c.YEAR = (
    SELECT MAX(YEAR)
    FROM cbb c2
    WHERE c2.TEAM LIKE CONCAT('%', n.college, '%')
);

-- Above avg
SELECT AVG(avg_pts) AS avg_pts_above,
       AVG(avg_gp) AS avg_gp_above
FROM (
    SELECT 
        n.player_name,
        AVG(n.pts) AS avg_pts,
        AVG(n.gp) AS avg_gp,
        MAX(c.W) AS college_wins
    FROM nba n
    JOIN cbb c
    ON c.TEAM LIKE CONCAT('%', n.college, '%')
    AND c.YEAR = n.draft_year
    WHERE n.draft_year IS NOT NULL
    GROUP BY n.player_name
) t
WHERE college_wins > 24.14;

-- Below Avg
SELECT AVG(avg_pts) AS avg_pts_below,
       AVG(avg_gp) AS avg_gp_below
FROM (
    SELECT 
        n.player_name,
        AVG(n.pts) AS avg_pts,
        AVG(n.gp) AS avg_gp,
        MAX(c.W) AS college_wins
    FROM nba n
    JOIN cbb c
    ON c.TEAM LIKE CONCAT('%', n.college, '%')
    AND c.YEAR = n.draft_year
    WHERE n.draft_year IS NOT NULL
    GROUP BY n.player_name
) t
WHERE college_wins <= 24.14; -- Players from above avg college teams tend to perform better in the NBA, avging approximately 8.37 points and 47.36 games per season, compared to 6.58 points and 42.10 games for players from below avg teams. This suggests that while college team success has a positive relationship with NBA performance, it is not a strong predictor, and individual player ability still plays a significant role.

-- Which colleges produce the best nba players
SELECT 
    n.college,
    AVG(n.pts) AS avg_pts,
    COUNT(DISTINCT n.player_name) AS num_players
FROM nba n
WHERE n.draft_year IS NOT NULL
GROUP BY n.college
HAVING COUNT(DISTINCT n.player_name) > 10
ORDER BY avg_pts DESC;
-- Wake Forest has the highest average points per player, followed by Oklahoma, UConn, and Kentucky.
-- This suggests that some smaller schools can produce very strong NBA scorers, while bigger schools usually provide more consistent output across a larger number of players.
-- The value "None" also appears often, which likely represents international players or missing college information.

-- Which college produced the most nba players?
SELECT 
    college,
    COUNT(DISTINCT player_name) AS num_players
FROM nba
WHERE draft_year IS NOT NULL
GROUP BY college
ORDER BY num_players DESC
LIMIT 10;
-- Certain colleges, especially major programs like Kentucky and Duke, consistently produce the highest number of NBA players.
-- This suggests that top college programs provide better exposure, competition, and development opportunities that increase the chances of reaching the NBA.

-- Is higher draft better performance
SELECT 
    draft_round,
    AVG(pts) AS avg_pts,
    AVG(gp) AS avg_gp
FROM nba
WHERE draft_round IS NOT NULL
GROUP BY draft_round;
-- First round picks perform better on average than second round picks in both scoring and games played.
-- This suggests that NBA scouting is relatively effective, since higher ranked prospects tend to perform better at the professional level.

-- How important is college success for draft pick?
SELECT 
    AVG(c.W) AS avg_college_wins,
    AVG(n.draft_number) AS avg_draft_pick
FROM nba n
JOIN cbb c
ON c.TEAM LIKE CONCAT('%', n.college, '%')
AND c.YEAR = n.draft_year
WHERE n.draft_number IS NOT NULL;
-- Players from stronger college teams are drafted slightly earlier, with an average draft position of about 21 compared to about 23 for players from weaker teams.
-- However, the difference is relatively small, suggesting that college team success has only a moderate influence on draft position.
-- So as long as the player is a strong candidate individually, them being from a worse college will not impact their draft severly.

-- Whats important for a player to have in the nba?
SELECT 
    player_name,
    AVG(pts + ast + reb) AS success_score
FROM nba
GROUP BY player_name
ORDER BY success_score DESC
LIMIT 10;
-- A success score was created by combining points, assists, and rebounds.
-- The top players score well across multiple categories, which suggests that well rounded performance is important for NBA success.

-- Bigger the programs better the players?
SELECT 
    n.college,
    AVG(n.gp) AS avg_games,
    COUNT(DISTINCT n.player_name) AS players
FROM nba n
GROUP BY n.college
HAVING players > 10
ORDER BY avg_games DESC;

-- Whats the best conference?
SELECT 
    c.CONF,
    AVG(n.pts) AS avg_pts,
    COUNT(DISTINCT n.player_name) AS players
FROM nba n
JOIN cbb c
ON c.TEAM LIKE CONCAT('%', n.college, '%')
AND c.YEAR = n.draft_year
GROUP BY c.CONF
ORDER BY avg_pts DESC;
-- After filtering out very small sample sizes, the Big 12 stands out as the top performing conference in terms of scoring.
-- At the same time, the SEC and ACC produce the most NBA players overall, showing both strong performance and consistency across major conferences.

-- Done by Arnav & Piyush.
-- What is considered the peak age in the NBA?
SELECT
    age,
    AVG(pts) AS avg_pts
FROM nba
GROUP BY age
ORDER BY age;
-- Player performance increases steadily in the early years, peaks around ages 26 to 29, and then gradually declines.
-- This suggests that the ideal age range for peak NBA performance is in the late twenties.

-- How important is height?
SELECT
    player_height,
    AVG(pts) AS avg_pts
FROM nba
GROUP BY player_height
ORDER BY player_height;
-- Players in the mid to tall height range tend to perform best overall, while very short players have lower average performance.
-- Taller players do better overall, but height is not everything by itself.

-- EDA for draft_data (international draft dataset, they have incomplete data in nba)
-- These queries combine the NBA dataset with the draft dataset to include international players and correct draft information.
-- The results show that players come from a mix of colleges and also from "None", which represents international players who did not attend college.
SELECT *
FROM draft_data
LIMIT 10;

SELECT 
d.player,
AVG(n.pts) AS avg_pts,
n.draft_number,
n.college
FROM nba n
JOIN draft_data d
ON n.player_name = d.player
GROUP BY d.player, n.draft_number, n.college;
-- Some international players, like Vlade Divac and Arvydas Sabonis, still have strong average performance, showing that the college system is not the only path to NBA success.
-- At the same time, many players from well-known colleges like Georgetown, Houston, and North Carolina also perform well, indicating that strong college programs still play an important role.
-- Overall, this supports the idea that both college development and international pathways contribute to NBA player success.

-- Do the top picks come from specific colleges? 
SELECT 
n.college,
ROUND(AVG(n.draft_number), 2) AS avg_pick,
COUNT(DISTINCT d.player) AS players
FROM draft_data d
JOIN nba n
ON n.player_name = d.player
WHERE n.draft_number > 0
AND n.college IS NOT NULL
AND n.college <> 'None'
GROUP BY n.college
HAVING players > 5
ORDER BY avg_pick;
-- Colleges like Kentucky have the lowest average draft pick (around 11.84), meaning their players are drafted earlier on average.
-- Other strong programs like Arizona, Syracuse, and Gonzaga also appear near the top, showing that certain schools consistently produce high draft prospects.
-- These are all well-known programs with strong competition and exposure, which likely helps players get noticed by NBA scouts.
-- Overall, this suggests that being part of a strong college program increases the chances of being drafted earlier.

-- Players from college
SELECT 
AVG(pts) AS avg_pts,
AVG(gp) AS avg_games,
AVG(draft_number) AS avg_pick,
COUNT(DISTINCT player_name) AS players
FROM nba
WHERE college IS NOT NULL
AND college != 'None'
AND draft_number IS NOT NULL;
-- On average, these players score about 8 points per game, play around 50 games, and are drafted around pick 17.
-- This suggests that most drafted college players have moderate NBA careers rather than being star players.
-- It also shows that while being drafted is important, not all drafted players end up performing at a very high level.

-- Players not from college
SELECT 
AVG(pts) AS avg_pts,
AVG(gp) AS avg_games,
AVG(draft_number) AS avg_pick,
COUNT(DISTINCT player_name) AS players
FROM nba
WHERE college = 'None'
OR college IS NULL;
-- These players average around 9 points per game and about 54 games, which is slightly higher than college players.
-- Their average draft position is around pick 19, which is slightly lower (worse) than college players who were drafted around pick 17.

-- Best international country
SELECT 
d.Nationality,
AVG(n.pts) AS avg_pts,
AVG(n.gp) AS avg_games,
COUNT(DISTINCT n.player_name) AS players
FROM nba n
JOIN draft_data d
ON n.player_name = d.Player
GROUP BY d.Nationality
HAVING COUNT(*) > 5
ORDER BY players DESC; -- Canada, France, Nigeria, Australia, Greece, Germany 
-- Canada has the highest number of players and also shows strong performance, making it one of the most consistent international sources of NBA talent.
-- Other countries like France, Australia, and Greece also produce a good number of players with solid average performance.
-- Some countries like Spain and Germany have fewer players but relatively high average points, suggesting strong individual talent from smaller samples.

-- Over time is there more belief in international players?
SELECT 
d.Draft,
AVG(n.draft_number) AS avg_pick
FROM nba n
JOIN draft_data d
ON n.player_name = d.Player
GROUP BY d.Draft
ORDER BY d.Draft; 
-- In earlier years, the average draft pick is much higher and more inconsistent, showing that players were often drafted later.
-- Over time, the average draft position becomes lower and more stable, indicating that players are being drafted earlier and with more consistency.
-- This suggests that scouting and evaluation have improved, and players (including international players) are being identified earlier in the draft.

-- Top international players?
SELECT 
n.player_name,
AVG(n.pts + n.ast + n.reb) AS success_score
FROM nba n
JOIN draft_data d
ON n.player_name = d.Player
GROUP BY n.player_name
ORDER BY success_score DESC
LIMIT 10; -- Joel, Giannis
-- Players like Joel Embiid, Giannis Antetokounmpo, and Karl-Anthony Towns rank at the top, showing very strong all-around performance.
-- Most of the top players contribute across multiple categories rather than excelling in just one, which suggests that well-rounded ability is important at the NBA level.
-- The presence of many international players in the top ranks shows that international development systems can produce elite NBA talent.

-- College vs International players
SELECT 
AVG(n.pts) AS intl_pts,
(SELECT AVG(pts) FROM nba WHERE college != 'None') AS college_pts
FROM nba n
JOIN draft_data d
ON n.player_name = d.Player; 
-- International players average around 8.78 points, while college players average around 8.07 points, which is a very small difference.
-- Just seems like if you are good enough, you can play.
-- It suggests that once players reach the NBA, their performance is more dependent on individual ability rather than their development path.

-- Tournament Seed vs Drafted Players
SELECT
    seed,
    COUNT(*) AS drafted_players
FROM cbb c
JOIN nba n
ON c.TEAM = n.college
WHERE seed > 0
GROUP BY seed
ORDER BY seed; 
-- Shows the number of drafted players per seed 
-- This is important because we want to see the most number of players in the lowest seeds
-- Lower the seed = better the placement of the college (team did that much better in the season) 

-- NBA teams drafting most number of international players 
SELECT
    `Picked by`,
    COUNT(*) AS international_players
FROM draft_data
WHERE `Picked by` IS NOT NULL
GROUP BY `Picked by`
ORDER BY international_players DESC
LIMIT 10;
-- Shows which teams grabbed the most number of international players 
-- Great to understand that there are teams who are heavily focused on international players 
-- and are able to recognize their immense talent despite not having attended a U.S. college basketball team

-- Countries producing highest draft picks
SELECT
    nationality,
    ROUND(AVG(Pick),2) AS avg_draft_pick
FROM draft_data
WHERE Pick IS NOT NULL
GROUP BY nationality
HAVING COUNT(*) >= 5
ORDER BY avg_draft_pick ASC
LIMIT 10;
-- Shows the avg draft numbers of a specific nationality 
-- Lower the draft pick = teams believe that player to be that much better 
-- So based on this, DR Congo has the lowest average and that way can overall have players who were drafted earliest the most

-- Done by Arnav & Piyush. 
/* Final Analysis */ 
WITH college_success AS (
    SELECT
        TEAM,
        YEAR,
        W,
        WAB,
        ADJOE,
        ADJDE,
        EFG_O,
        TOR,
        CASE
            WHEN WAB >= (SELECT AVG(WAB) FROM cbb)
             AND ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
             AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
             AND EFG_O >= (SELECT AVG(EFG_O) FROM cbb)
             AND TOR <= (SELECT AVG(TOR) FROM cbb)
                THEN 'Strong Success Profile'
            ELSE 'Weaker Success Profile'
        END AS success_group
    FROM cbb
),
player_summary AS (
    SELECT
        player_name,
        college,
        draft_year,
        draft_number,
        AVG(pts) AS avg_pts,
        AVG(gp) AS avg_gp,
        AVG(reb) AS avg_reb,
        AVG(ast) AS avg_ast
    FROM nba
    WHERE college IS NOT NULL
    AND college <> 'None'
    GROUP BY player_name, college, draft_year, draft_number
)
SELECT
    cs.success_group,
    COUNT(DISTINCT ps.player_name) AS players,
    ROUND(AVG(ps.draft_number), 2) AS avg_draft_pick,
    ROUND(AVG(ps.avg_pts), 2) AS avg_nba_points,
    ROUND(AVG(ps.avg_gp), 2) AS avg_games_played,
    ROUND(AVG(ps.avg_reb), 2) AS avg_rebounds,
    ROUND(AVG(ps.avg_ast), 2) AS avg_assists
FROM college_success cs
JOIN player_summary ps
ON cs.TEAM LIKE CONCAT('%', ps.college, '%')
AND cs.YEAR = ps.draft_year
WHERE ps.draft_number > 0
GROUP BY cs.success_group;
-- This query compares NBA outcomes for players from Strong Success Profile teams and Weaker Success Profile teams.
-- This shows a mixed result: stronger college team success is connected to slightly better NBA production,
-- but it does not clearly mean players are drafted earlier or play more games in the NBA.

WITH player_summary AS (
    SELECT
        player_name,
        college,
        draft_year,
        draft_number,
        AVG(pts) AS avg_pts,
        AVG(gp) AS avg_gp
    FROM nba
    WHERE college IS NOT NULL
    AND college <> 'None'
    GROUP BY player_name, college, draft_year, draft_number
)
SELECT
    CASE
        WHEN c.POSTSEASON IS NOT NULL AND c.POSTSEASON <> ''
            THEN 'Postseason Team'
        ELSE 'Non-Postseason Team'
    END AS team_group,
    COUNT(DISTINCT ps.player_name) AS players,
    ROUND(AVG(ps.draft_number), 2) AS avg_draft_pick,
    ROUND(AVG(ps.avg_pts), 2) AS avg_nba_points,
    ROUND(AVG(ps.avg_gp), 2) AS avg_games_played
FROM cbb c
JOIN player_summary ps
ON c.TEAM LIKE CONCAT('%', ps.college, '%')
AND c.YEAR = ps.draft_year
WHERE ps.draft_number > 0
GROUP BY team_group
ORDER BY avg_draft_pick;
-- This query compares drafted players from postseason college teams against drafted players from non-postseason teams.
-- This suggests that postseason team success may help players get drafted slightly earlier and score slightly more,
-- but it does not guarantee a longer NBA role or more games played.

WITH college_success AS (
    SELECT
        TEAM,
        YEAR,
        CONF,
        W,
        WAB,
        ADJOE,
        ADJDE,
        EFG_O,
        TOR,
        CASE
            WHEN WAB >= (SELECT AVG(WAB) FROM cbb)
             AND ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
             AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
             AND EFG_O >= (SELECT AVG(EFG_O) FROM cbb)
             AND TOR <= (SELECT AVG(TOR) FROM cbb)
                THEN 'Strong Success Profile'
            ELSE 'Weaker Success Profile'
        END AS success_group
    FROM cbb
),
player_summary AS (
    SELECT
        player_name,
        college,
        draft_year,
        draft_round,
        draft_number,
        ROUND(AVG(pts), 2) AS avg_pts,
        ROUND(AVG(gp), 2) AS avg_gp,
        ROUND(AVG(reb), 2) AS avg_reb,
        ROUND(AVG(ast), 2) AS avg_ast,
        ROUND(AVG(pts + reb + ast), 2) AS success_score
    FROM nba
    WHERE college IS NOT NULL
    AND college <> 'None'
    AND draft_number > 0
    GROUP BY player_name, college, draft_year, draft_round, draft_number
)
SELECT
    ps.player_name,
    ps.college,
    cs.TEAM AS college_team,
    cs.YEAR,
    cs.CONF,
    cs.success_group,
    cs.W AS college_wins,
    cs.WAB,
    cs.ADJOE,
    cs.ADJDE,
    cs.EFG_O,
    cs.TOR,
    ps.draft_round,
    ps.draft_number,
    ps.avg_pts,
    ps.avg_gp,
    ps.avg_reb,
    ps.avg_ast,
    ps.success_score
FROM player_summary ps
JOIN college_success cs
ON cs.TEAM LIKE CONCAT('%', ps.college, '%')
AND cs.YEAR = ps.draft_year
ORDER BY cs.success_group, ps.draft_number;
-- This query shows the player-level connection between college team success and NBA draft outcomes.
-- Instead of only comparing group averages, this output lists the actual drafted players, their college team,
-- the team's success profile, and the player's NBA averages.
-- This makes the analysis more useful from a draft evaluator point of view because NBA teams draft individual players.
-- Examples from the results include players like Karl-Anthony Towns from Kentucky, Zion Williamson from Duke,
-- Paolo Banchero from Duke, Jayson Tatum from Duke, and Jaylen Brown from California.
-- These players came from Strong Success Profile teams and were also high draft picks with strong NBA production.
-- Overall, this query helps show that strong college teams can produce highly drafted NBA players. 

WITH college_success AS (
    SELECT
        TEAM,
        YEAR,
        CONF,
        W,
        WAB,
        ADJOE,
        ADJDE,
        EFG_O,
        TOR,
        CASE
            WHEN WAB >= (SELECT AVG(WAB) FROM cbb)
             AND ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
             AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
             AND EFG_O >= (SELECT AVG(EFG_O) FROM cbb)
             AND TOR <= (SELECT AVG(TOR) FROM cbb)
                THEN 'Strong Success Profile'
            ELSE 'Weaker Success Profile'
        END AS success_group
    FROM cbb
),
player_summary AS (
    SELECT
        player_name,
        college,
        draft_year,
        draft_round,
        draft_number,
        ROUND(AVG(pts), 2) AS avg_pts,
        ROUND(AVG(gp), 2) AS avg_gp,
        ROUND(AVG(reb), 2) AS avg_reb,
        ROUND(AVG(ast), 2) AS avg_ast,
        ROUND(AVG(pts + reb + ast), 2) AS success_score
    FROM nba
    WHERE college IS NOT NULL
    AND college <> 'None'
    AND draft_number > 0
    GROUP BY player_name, college, draft_year, draft_round, draft_number
)
SELECT
    ps.player_name,
    ps.college,
    cs.TEAM AS college_team,
    cs.YEAR,
    cs.success_group,
    ps.draft_number,
    ps.avg_pts,
    ps.avg_gp,
    ps.avg_reb,
    ps.avg_ast,
    ps.success_score
FROM player_summary ps
JOIN college_success cs
ON cs.TEAM LIKE CONCAT('%', ps.college, '%')
AND cs.YEAR = ps.draft_year
WHERE cs.success_group = 'Strong Success Profile'
ORDER BY ps.success_score DESC;
-- This query focuses only on players from teams with a Strong Success Profile and ranks them by NBA production.
-- The results show several high-performing NBA players from successful college team profiles, such as Karl-Anthony Towns,
-- Zion Williamson, Devin Booker, Jayson Tatum, Paolo Banchero, De'Aaron Fox, Domantas Sabonis, and Brandon Ingram.
-- These players had strong NBA averages after being drafted, which supports the idea that successful college team environments
-- can be connected to strong individual NBA outcomes.
-- However, the results also show that this is not just about being drafted high, because players like Donovan Mitchell,
-- Devin Booker, and Bam Adebayo were not top five picks but still had strong NBA production.
-- Overall, this query helps show which individual players best connect college team success to later NBA performance.

WITH college_success AS (
    SELECT
        TEAM,
        YEAR,
        CONF,
        W,
        WAB,
        ADJOE,
        ADJDE,
        EFG_O,
        TOR,
        CASE
            WHEN WAB >= (SELECT AVG(WAB) FROM cbb)
             AND ADJOE >= (SELECT AVG(ADJOE) FROM cbb)
             AND ADJDE <= (SELECT AVG(ADJDE) FROM cbb)
             AND EFG_O >= (SELECT AVG(EFG_O) FROM cbb)
             AND TOR <= (SELECT AVG(TOR) FROM cbb)
                THEN 'Strong Success Profile'
            ELSE 'Weaker Success Profile'
        END AS success_group
    FROM cbb
),
player_summary AS (
    SELECT
        player_name,
        college,
        draft_year,
        draft_round,
        draft_number,
        ROUND(AVG(pts), 2) AS avg_pts,
        ROUND(AVG(gp), 2) AS avg_gp,
        ROUND(AVG(reb), 2) AS avg_reb,
        ROUND(AVG(ast), 2) AS avg_ast
    FROM nba
    WHERE college IS NOT NULL
    AND college <> 'None'
    AND draft_number > 0
    GROUP BY player_name, college, draft_year, draft_round, draft_number
)
SELECT
    ps.player_name,
    ps.college,
    cs.TEAM AS college_team,
    cs.YEAR,
    cs.success_group,
    cs.W AS college_wins,
    cs.WAB,
    ps.draft_round,
    ps.draft_number,
    ps.avg_pts,
    ps.avg_gp,
    ps.avg_reb,
    ps.avg_ast
FROM player_summary ps
JOIN college_success cs
ON cs.TEAM LIKE CONCAT('%', ps.college, '%')
AND cs.YEAR = ps.draft_year
WHERE cs.success_group = 'Strong Success Profile'
ORDER BY ps.draft_number; 
-- This query shows the highest drafted players who came from teams with a Strong Success Profile.
-- Since the results are ordered by draft_number, the first rows show the earliest NBA draft picks from successful college teams.
-- The output includes examples like Karl-Anthony Towns from Kentucky, Zion Williamson from Duke,
-- Paolo Banchero from Duke, Deandre Ayton from Arizona, Jayson Tatum from Duke, and De'Aaron Fox from Kentucky.
-- These examples show that strong college teams often had players who were selected very early in the NBA draft.
-- However, the NBA averages also show that draft position and team success do not always lead to the same level of NBA production.
-- Overall, this query supports the idea that successful college teams can improve a player's draft visibility.


/* The core question of this project was: What helps a college basketball team be successful and how does that translate to their players being drafted into the NBA? 
Based on the SQL analysis, the main factors connected to college team success were not just wins alone. 
The stronger teams usually had a better overall team profile, especially higher WAB, stronger offensive efficiency through ADJOE, better shooting efficiency through EFG_O, lower defensive rating through ADJDE, and lower turnover rate through TOR. 
These stats show that successful college teams usually combine efficient scoring, stronger defense, and fewer mistakes.

After identifying these team success factors, the next step was to connect them to NBA draft outcomes. 
For this part of the analysis, we included the player’s individual NBA data because the SQL queries have to connect college teams to actual drafted players. 
The project uses both the team’s college success profile and each player’s NBA draft and performance data, such as draft number, average points, games played, rebounds, and assists.

The results also highlight this. 
Just using teams' data leads to similar statistics for each team whether they are strong/weak, postseason/non-postseason etc. 
However, taking player data into account makes things clear. 

Players such as Karl-Anthony Towns, Zion Williamson, Paolo Banchero, Jayson Tatum, Devin Booker, De’Aaron Fox, Domantas Sabonis, and Brandon Ingram show how successful college programs can produce strong NBA players.
These examples support the idea that strong college team environments can help players develop and gain visibility. 
At the same time, individual NBA performance still depends on the player, not only the team they came from. Some players from strong college teams became stars, while others had more average NBA careers. 
Some players from weaker team profiles also played many NBA games or performed well.

Even in the case of international players who haven't had a U.S. college basketball experience are able to be drafted and perform well in the NBA. 
Showing that even metrics other than college basketball team success exist and that it is not required to be able to see success in the NBA. 

Overall, the answer to the core question is that college basketball success is helped by a strong team profile: efficient offense, solid defense, good shooting, high WAB, and fewer turnovers.
That success can translate to NBA draft outcomes by giving players more visibility, better competition, and a stronger basketball environment.
However, it does not guarantee that a player will be drafted earlier or become more successful in the NBA.
College team success is useful context for evaluating NBA prospects, but the player's individual data must still be included because the draft is ultimately based on individual players, not just team success. */